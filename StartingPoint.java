package sparkProject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.TransformerException;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;

import scala.Function;
import scala.Function1;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.DataFrameReader;
import static org.apache.spark.sql.functions.callUDF;

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

		// Entry oint for Dataset and DataFrame

		SparkConf conf = new SparkConf();
		SparkSession spark = SparkSession.builder().config(conf).appName("Spark Program").master("local").getOrCreate();

		/*
		 * Entry point for Spark RDDs SparkConf conf = new
		 * SparkConf().setAppName("Spark Program").setMaster("local"); JavaSparkContext
		 * sc = new JavaSparkContext(conf);
		 */

		Dataset<Row> ds1 = Transformations.jsonDatasetOriginal(spark,
				context.getProperty(GlobalConstants.ReadyJsonFile));

		Dataset<Row> ds1_populationGt100k = ds1.filter(ds1.col("countryPopulation").cast("int").$greater(140000000))
				.alias("ds1_population");

		Dataset<Row> ds1_countryNameContain_ia = ds1.filter(ds1.col("countryName").contains("ia")).alias("ds1_country");

		Dataset<Row> ds2_populationGt100k_with_ia = ds1_populationGt100k.join(ds1_countryNameContain_ia,
				ds1_populationGt100k.col("countrySize").equalTo(ds1_countryNameContain_ia.col("countrySize")));

		Dataset<Row> ds2_populationGt100k_with_ia_filters_only = ds1_populationGt100k
				.filter(ds1_populationGt100k.col("countryName").contains("ia"));

		ds1_populationGt100k.show(5);
		ds1_countryNameContain_ia.show(5);

		ds2_populationGt100k_with_ia.show();

		ds2_populationGt100k_with_ia_filters_only.show();

		UDFfunctions.registerCountryCodeFunction(spark);

		Dataset<String> op = ds2_populationGt100k_with_ia_filters_only
				.withColumn("countryCode",
						callUDF("registerCountryCodeFunctionUDF",
								ds2_populationGt100k_with_ia_filters_only.col("countryName")))
				.map((MapFunction<Row, String>) row -> row.toString(), Encoders.STRING());

		op.map(new MapFunction<String, String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String call(String value) throws Exception {

				return value;
			}

		}, Encoders.STRING()).toDF();

	}

}
