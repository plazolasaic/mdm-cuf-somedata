package mdm.cuf.somedata.server;

import java.util.Arrays;
import mdm.cuf.core.server.MdmCufSpringApplicationBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the actual Spring boot application that loads up the entire app.
 *
 * @author darias
 */
@SpringBootApplication
@Import(SomeDataServerConfig.class)
@ComponentScan(basePackages = "mdm.cuf.somedata.server", excludeFilters = @Filter(Configuration.class))
public class SomeDataServerApplication extends SpringBootServletInitializer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SomeDataServerApplication.class);

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(SomeDataServerApplication.class);
    }

    public static void main(final String[] args) throws Exception {
        final ConfigurableApplicationContext context = new MdmCufSpringApplicationBuilder(SomeDataServerApplication.class).build().run(args);
        final String[] profiles = context.getEnvironment().getActiveProfiles();
        if(LOGGER.isInfoEnabled()) 
            LOGGER.info("!!!!!!!!!!!!!!!!!! Active Profiles: "+ Arrays.toString(profiles) + "!!!!!!!!!!!!!!!!");
    }
}


