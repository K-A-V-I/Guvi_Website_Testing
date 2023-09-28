package GuviTest_Project;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/java/Feature/Guvi.feature",
        glue = {"GuviTest_Project"},
        publish=true,
        plugin = {"pretty", "html:target/cucumber-reports"}
)

public class RunTest {

}
