package sparkProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class XmltoJson {

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	public static String TEST_XML_STRING = (String) XMLTransformer.transformedXMLMap
			.get(GlobalConstants.TransformedXML);
	protected static Map<String, Object> readyJson = new HashMap<String, Object>();

	public static void XmltoJsonprocessor(Properties context) throws IOException {
		try {

			File jsonFile = new File(context.getProperty(GlobalConstants.ReadyJsonFile));
			JSONObject xmlJSONObj = XML
					.toJSONObject((String) XMLTransformer.transformedXMLMap.get(GlobalConstants.TransformedXML));
			String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

			if (!jsonFile.exists()) {
				System.out.println(context.getProperty(GlobalConstants.ReadyJsonFile) + " File Created");

				FileWriter writer = new FileWriter(jsonFile);
				writer.append(jsonPrettyPrintString.replace("\n","").replace("\r", "").replaceAll("\\s+", ""));
				writer.close();

			} else
				System.out.println("File " + context.getProperty(GlobalConstants.ReadyJsonFile) + " already exists");

			XmltoJson.readyJson.put(GlobalConstants.ReadyJson, jsonPrettyPrintString);
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
	}

}
