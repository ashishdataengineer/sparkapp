package sparkProject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

public class XMLTransformer {
	
	protected static Map<String, Object> transformedXMLMap = new HashMap<String, Object>();
	
	public static void xmlProcessor(String stylesheetPathname, String inputPathname, String outputPathname ) throws TransformerException, FileNotFoundException {

		Source inputXml = new StreamSource(new File(inputPathname));
		Source xsl = new StreamSource(new File(stylesheetPathname));
		StringWriter sw = new StringWriter();
		Result outputResult = new StreamResult(sw);
		
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer(xsl);
			transformer.transform(inputXml, outputResult);
			transformedXMLMap.put(GlobalConstants.TransformedXML, sw.toString());
			
		} catch (TransformerException e) {
			e.printStackTrace();
		}


	}

}
