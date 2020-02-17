package sparkProject;

import org.apache.log4j.PropertyConfigurator;

public class Log4jLoader {

	private String log4jConfig;

	public String getLog4jConfig() {

		return log4jConfig;
	}

	public void setLog4jConfig(String log4jConfig) {

		this.log4jConfig = log4jConfig;
		setLog4jProperty(log4jConfig);
	}

	private static void setLog4jProperty(String log4jConfig) {

		PropertyConfigurator.configure(log4jConfig);

	}

}
