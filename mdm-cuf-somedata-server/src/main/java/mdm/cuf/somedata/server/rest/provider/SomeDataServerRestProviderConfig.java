package mdm.cuf.somedata.server.rest.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.MessageSource;

import mdm.cuf.core.security.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.models.OpenAPI;
import mdm.cuf.core.rest.OpenAPICommon;
import org.springdoc.core.models.GroupedOpenApi;
import mdm.cuf.somedata.bio.SomeDataBio;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * The SomeDataBio Rest End point config, swagger end point registration
 *
 * @author darias
 */

public class SomeDataServerRestProviderConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SomeDataServerRestProviderConfig.class);

    @Autowired
    private SecurityProperties properties;

    @Autowired
    private MessageSource messageSource;

    @Value("classpath:service-doc/service-description.md")
    private Resource resource;

    @Bean
    public OpenAPI customOpenAPI() throws IOException {
        String title = "SomeData API";
        String description = null;
        try (InputStream is = resource.getInputStream()) {
            return OpenAPICommon.createOpenAPI(title, new String(is.readAllBytes(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            return new OpenAPI();
        }
    }

    private static void safeClose(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.warn("InputStream failed to close",e);
            }
        }
    }

    /**
     * Cuf somedata bio v1 api.
     *
     * @return the GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi cufSomeDataBioV1RestApi() {
        return OpenAPICommon.createCommonBioGroupedOpenApi(SomeDataBio.class, SomeDataBioRestProvider.VERSION,
                SomeDataBioRestProvider.URL_PREFIX, messageSource, properties);
    }
}
