package sparkProject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class JSONprocessing {

	String defaultContextFilePath;
	String jsonFilePath;
	JsonObject jsonBodyObject;
	private Gson gson = new Gson();

	public void setjsonFilePath(String jsonFilePath) {

		this.jsonFilePath = jsonFilePath;
	}

	public void setdefaultContextFilePath(String defaultContextFilePath) {

		this.defaultContextFilePath = defaultContextFilePath;
	}

	public void setJsonFilePathLoad(Properties context) {

		BufferedReader bufferReader;
		try {

			bufferReader = new BufferedReader(new FileReader(this.jsonFilePath));
			JsonParser jsonParser = new JsonParser();
			jsonBodyObject = jsonParser.parse(bufferReader).getAsJsonObject();
			context.put(GlobalConstants.JSON_FILE_OBJECT, jsonBodyObject);

		} catch (FileNotFoundException fio) {
			fio.getMessage();

		}

	}

	public JsonObject getJsonObject(Properties context) {

		return this.jsonBodyObject.getAsJsonObject().getAsJsonObject("person");

	}

	public void setJsonObjectContext(Properties context, JsonObject jsonObject) {
		Set<Entry<String, JsonElement>> entrySet = jsonObject.getAsJsonObject().entrySet();
		for (Map.Entry<String, JsonElement> entry : entrySet) {
			
			//System.out.println(entry.getKey()+":"+entry.getValue().toString());
			if (!entry.getValue().isJsonArray() && !entry.getValue().isJsonObject()) {				
				System.out.println(entry.getKey()+":"+entry.getValue().toString());
				context.setProperty(entry.getKey(), entry.getValue().getAsString());
			}

		}

	}

	@SuppressWarnings("unchecked")
	public <T> void startLoadingJSON(Properties context) {

		BufferedReader bufferReader;
		T jsonObject = null;

		try {

			bufferReader = new BufferedReader(new FileReader(this.jsonFilePath));
			jsonObject = (T) gson.fromJson(bufferReader, JsonMainBody.class);
			context.put(GlobalConstants.JSON_LOADING_OBJECT, jsonObject);

			// ((JsonMainBody)jsonObject).getPerson().getDateOfBirth();

		} catch (FileNotFoundException fio) {
			fio.getMessage();

		}

	}

	public void loadDeaultContext(Properties context) {

		Properties newContext = new Properties();
		InputStream readdefaultContext;

		try {
			readdefaultContext = new FileInputStream(defaultContextFilePath);
			newContext.load(readdefaultContext);

			for (String key : newContext.keySet().toArray(new String[0])) {

				System.out.println(key);

				context.setProperty(key, newContext.getProperty(key));
			}
		} catch (IOException io) {

			io.getMessage();
		}

	}

}
