package sparkProject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CommonLogger {

	public static void logDebugMessage(Logger logger, String className, String methodName, String logMessage) {

		StringBuilder prepareLogMessage = new StringBuilder();
		if (logger.isDebugEnabled()) {
			prepareLogMessage.append(
					"In Class: " + className + "|" + "In Method: " + methodName + "|" + "Message: " + logMessage);
			logger.log(CommonLogger.class.getCanonicalName(), Level.DEBUG, prepareLogMessage, null);
		}

	}

	public static void logErrorMessage(Logger logger, String className, String methodName, String logMessage) {

		StringBuilder prepareLogMessage = new StringBuilder();
		if (logger.isDebugEnabled()) {
			prepareLogMessage.append(
					"In Class: " + className + "|" + "In Method: " + methodName + "|" + "Message: " + logMessage);
			logger.log(CommonLogger.class.getCanonicalName(), Level.ERROR, prepareLogMessage, null);
		}

	}

}
