package mdm.cuf.somedata.server;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import mdm.cuf.core.AbstractMdmCufCoreProperties;

/**
 * Application properties, they can be wired up by using mdm-cuf-somedata placeholder in yml
 *
 * @author darias
 */
@Component
@ConfigurationProperties(prefix = "mdm-cuf-somedata")
public class SomeDataServerProperties extends AbstractMdmCufCoreProperties {


}
