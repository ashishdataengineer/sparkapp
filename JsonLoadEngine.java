package sparkProject;
import java.util.Properties;

public class JsonLoadEngine {

	public static void initialize(Properties context) {

		((LoadingJson) AbstractProcessingLoad.getProcessor(GlobalConstants.JSON_LOADER)).initializeJson(context);

	}

}
