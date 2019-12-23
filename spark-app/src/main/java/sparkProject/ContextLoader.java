package sparkProject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.Resource;

public class ContextLoader {

	protected static Map<String, Object> globalMap = new HashMap<String, Object>();

	static {

		ApplicationContext loadFromXMLs = new ClassPathXmlApplicationContext(
				loadXMLConfigurationFromClassPath(GlobalConstants.SPRING_CONTEXT_PATTERN));
		globalMap.put(GlobalConstants.CONTEXT, loadFromXMLs);

	}

	public static ApplicationContext loadContext() {

		if (globalMap.get(GlobalConstants.CONTEXT) == null) {
			ApplicationContext loadFromXMLs = new ClassPathXmlApplicationContext(
					loadXMLConfigurationFromClassPath(GlobalConstants.SPRING_CONTEXT_PATTERN));
			globalMap.put(GlobalConstants.CONTEXT, loadFromXMLs);

		}

		return (ApplicationContext) globalMap.get(GlobalConstants.CONTEXT);

	}

	public static String[] loadXMLConfigurationFromClassPath(String pattern) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		try {

			Resource[] configFiles;
			configFiles = resolver.getResources(GlobalConstants.CLASSPATH + pattern);
			String[] stringArray = new String[configFiles.length];
			for (int i = 0; i < configFiles.length; i++) {
				String fileName = new File(configFiles[i].toString()).getName();
				stringArray[i] = fileName.substring(0, fileName.length() - 1);

			}
			return stringArray;
		} catch (IOException i) {
			i.getStackTrace();

		}

		return null;

	}

}
