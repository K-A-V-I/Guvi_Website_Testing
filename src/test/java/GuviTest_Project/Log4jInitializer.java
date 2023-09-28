package GuviTest_Project;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitializer {
	static {
        PropertyConfigurator.configure("log4j.properties");
    }

}
