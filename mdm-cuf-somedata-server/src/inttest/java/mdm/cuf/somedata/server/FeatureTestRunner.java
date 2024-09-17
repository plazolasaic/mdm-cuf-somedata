package mdm.cuf.somedata.server;

import mdm.cuf.somedata.server.config.SomeDataTestConfig;
import mdm.cuf.core.server.AbstractMdmCufCoreServerSpringTest;
import mdm.cuf.core.test.cucumber.annotation.SpringCucumberOptions;
import mdm.cuf.core.test.runner.SpringCucumberRunnerKafkaEnabled;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(SpringCucumberRunnerKafkaEnabled.class)
@SpringCucumberOptions(
		glue = { "mdm.cuf.somedata" },
        features = { "src/main/resources/static/features" },
        tags = { "@Feature" },
        threads = 10)
@ContextConfiguration(classes = { SomeDataTestConfig.class})
public class FeatureTestRunner extends AbstractMdmCufCoreServerSpringTest {
}
