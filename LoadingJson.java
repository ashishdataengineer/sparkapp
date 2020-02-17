package sparkProject;

import java.util.Properties;

import com.google.gson.JsonObject;

public class LoadingJson implements Processor {

	public JSONprocessing Jsonloadingprocess;

	public void setJsonloadingprocess(JSONprocessing jsonloadingprocess) {

		this.Jsonloadingprocess = jsonloadingprocess;
	}
	
	public JSONprocessing getJsonloadingprocess() {

		return Jsonloadingprocess;
	}
	
	public void initializeJson(Properties context) {

		Jsonloadingprocess.setJsonFilePathLoad(context);
		Jsonloadingprocess.startLoadingJSON(context);
		JsonObject jsonObject = Jsonloadingprocess.getJsonObject(context);
		Jsonloadingprocess.setJsonObjectContext(context, jsonObject);
		//Jsonloadingprocess.loadDeaultContext(context);  for "=" separator file

	}

}
