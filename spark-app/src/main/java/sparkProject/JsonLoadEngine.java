package sparkProject;

import java.util.Properties;

public class JsonLoadEngine {

	public static void initialize(Properties context) {

		((LoadingJson) AbstractProcessingLoad.getProcessor(GlobalConstants.JSON_LOADER)).initializeJson(context);

	}

	public static void matchData() {

		String ecdfMatchingBaseBase = "ecdfMatchingBaseBase";
		System.out.println("Error");
		//((MatchingBasePhase) AbstractProcessingLoad.getProcessor(ecdfMatchingBaseBase)).matchData();

	}

}
