package mdm.cuf.somedata.server.processor.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mdm.cuf.core.messages.MessageSeverity;
import mdm.cuf.somedata.server.SomeDataServerProperties;
import mdm.cuf.somedata.server.dio.repository.SomeDataDioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import mdm.cuf.core.messages.Message;
import mdm.cuf.core.server.processor.AbstractBioValidator;
import mdm.cuf.somedata.bio.SomeDataBio;


/**
 * Validator for SomeData Bio.  Iterates over the SomeData bio graph calling the validator on each sub bio in the graph
 *
 * @author darias
 */
@Component
public class SomeDataBioValidator extends AbstractBioValidator<SomeDataBio> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomeDataBioValidator.class);

    @Autowired
    private SomeDataDioRepository someDataDioRepository;

    @Autowired
    private SomeDataServerProperties someDataServerProperties;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<Message> fullValidation(SomeDataBio bioPrevious, SomeDataBio bioNew, Class<?>... validationHints) {
        final List<Message> errorList = new ArrayList<>();

        if (bioPrevious == null) {
            LOGGER.debug("%%%%%  Adding new Bio");
            if(bioNew.getSomeDataId() == null) {
                errorList.add(createMessage(MessageSeverity.INFO, "missing_id", null));
            }
        } else {
            LOGGER.debug("%%%%%  Updating existing Bio");
        }

        return errorList;
    }

    private Message createMessage(MessageSeverity severity, String key, Object[] args) {
        Locale locale = new Locale("en","US");
        Message message = new Message();
        message.setSeverity(severity);
        message.setKey(key);
        message.setCode(messageSource.getMessage(key + ".code", null, locale));
        message.setText(messageSource.getMessage(key, args, locale));
        return message;
    }
    
}
