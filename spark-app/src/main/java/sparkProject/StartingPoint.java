package sparkProject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Row;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class StartingPoint {

	private static Logger logger = Logger.getLogger(StartingPoint.class);

	public static void main(String[] args) throws TransformerException, IOException, AnalysisException {

		AbstractProcessingLoad.getProcessor();
		CommonLogger.logDebugMessage(logger, "Mainclass", "MainMethod", "I'm writing logs");
		Properties context = new Properties();
		JsonLoadEngine.initialize(context);
		System.out.print("InputXMLPath:" + context.getProperty("InputXMLPath"));
		XMLTransformer.xmlProcessor(context.getProperty("XMLXsltPath"), context.getProperty("InputXMLPath"),
				context.getProperty("TransformedXMLOutputPath"));
		XmltoJson.XmltoJsonprocessor(context);

		//Entry oint for Dataset and DataFrame
		SparkSession spark = SparkSession.builder().appName("Spark Program").master("local").getOrCreate();
		
		/*
		 * Entry point for Spark RDDs
		 * SparkConf conf = new
		 * SparkConf().setAppName("Spark Program").setMaster("local"); JavaSparkContext
		 * sc = new JavaSparkContext(conf);
		 */
		
		Dataset<Row> ds = spark.read().schema(Jsonreadystructure.SCHEMA)
				.json(context.getProperty(GlobalConstants.ReadyJsonFile));
		
		ds.createOrReplaceTempView("country_data");
		Dataset<Row> df = spark.sql("SELECT country.name FROM country_data lateral view explode(countries.country) t as country"); 
		df.show();
		 
		

	}

}
