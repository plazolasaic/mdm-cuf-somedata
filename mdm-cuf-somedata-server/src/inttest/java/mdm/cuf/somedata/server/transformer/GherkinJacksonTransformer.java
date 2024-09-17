package mdm.cuf.somedata.server.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class GherkinJacksonTransformer implements TableCellByTypeTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GherkinJacksonTransformer.class);

    private static final List<String> DATE_PLACEHOLDERS = Stream.of("now", "yesterday", "tomorrow").collect(Collectors.toList());

    private static final List<Class> DATE_TYPES =
            Stream.of(Date.class, Timestamp.class, ZonedDateTime.class).collect(Collectors.toList());

    private final ObjectMapper gherkinObjectMapper;

    public GherkinJacksonTransformer(final ObjectMapper gherkinObjectMapper) {
        this.gherkinObjectMapper = gherkinObjectMapper;
    }

    @DefaultParameterTransformer
    @DefaultDataTableCellTransformer
    public Object defaultTransformer(Object fromValue, Type toValueType) {
        return gherkinObjectMapper.convertValue(fromValue, gherkinObjectMapper.constructType(toValueType));
    }

    @Override
    public Object transform(String cellValue, Type toValueType) throws Throwable {
        return gherkinObjectMapper.convertValue(cellValue, gherkinObjectMapper.constructType(toValueType));
    }

    @DefaultDataTableEntryTransformer
    public <T> T transform(Map<String, String> map, Type type) {
        var aClass = (Class<T>)type;
        Map<String, String> modifiedMap = setDynamicDates(map, aClass);
        return transformNestedValues(modifiedMap, aClass);
    }

    public <T> T transformNestedValues(final Map<String, String> entry, final Class<T> type) {
        final Map<String, Map<String, String>> nestedEntries = entry.entrySet().stream().filter(e -> e.getKey().contains(".")).filter(e -> e.getValue() != null)
                .collect(groupingBy(e -> e.getKey().substring(0, e.getKey().indexOf('.')), HashMap::new,
                        toMap(e -> e.getKey().substring(e.getKey().indexOf('.') + 1), Entry::getValue)));
        final Map<String, Object> nestedValues = new HashMap<>();
        for (Entry<String, Map<String, String>> mapEntry : nestedEntries.entrySet()) {
            String k = mapEntry.getKey();
            Map<String, String> v = mapEntry.getValue();
            Class<?> nestedType = findType(type, k);
            if (nestedType != null) {
                nestedValues.put(k, transform(v, nestedType));
            }
        }
        var filteredEntries = entry.entrySet().stream().filter(e -> !e.getKey().contains(".")).collect(Collectors.toList());
        filteredEntries.forEach(filteredEntry -> nestedValues.put(filteredEntry.getKey(), filteredEntry.getValue()));

        return gherkinObjectMapper.convertValue(nestedValues, type);
    }

    public <T> Map<String, String> setDynamicDates(Map<String, String> map, final Class<T> type) {
        Map<String, String> clone = new HashMap<>(map);
        for (Entry<String, String> field : map.entrySet()) {
            String fieldName = field.getKey();
            if (clone.get(fieldName) != null && DATE_PLACEHOLDERS.contains(clone.get(fieldName)) && isDateField(fieldName, type)) {
                clone.put(fieldName, getDate(map.get(fieldName)));
            }
        }
        return clone;
    }

    public <T> boolean isDateField(final String fieldName, Class<T> type) {
        final BeanWrapper bean = new BeanWrapperImpl(type);
        for (PropertyDescriptor pd : bean.getPropertyDescriptors()) {
            if (fieldName.equals(pd.getName()) && DATE_TYPES.contains(pd.getReadMethod().getReturnType())) {
                return true;
            }
        }
        return false;
    }

    public String getDate(String inDate) {
        if (StringUtils.isEmpty(inDate)) {
            return null;
        }
        Date result = getDateByString(inDate);
        return result != null ? gherkinObjectMapper.convertValue(result, String.class) : null;
    }

    public Class<?> findType(final Class type, String fieldName) {
        try {
            return type.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException | SecurityException e) {
            LOGGER.warn("Not able to get field Type by reflection", e);
        }
        return null;
    }

    public Date getDateByString(String date) {
        if ("now".equalsIgnoreCase(date)) {
            return new Date();
        } else if ("yesterday".equalsIgnoreCase(date)) {
            return getDateByOffset(new Date(), -1);
        } else if ("tomorrow".equalsIgnoreCase(date)) {
            return getDateByOffset(new Date(), 1);
        }
        return null;
    }

    private Date getDateByOffset(Date date, int offset) {
        Date beforeDate = new Date();
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, offset);
            beforeDate = cal.getTime();
        }
        return beforeDate;
    }
}
