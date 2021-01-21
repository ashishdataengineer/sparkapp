package sparkProject;

import java.io.File;
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
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.types.DataTypes;

import scala.Function;
import scala.Function1;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.DataFrameReader;

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
		SparkContext sc = spark.sparkContext();
		/*
		 * Entry point for Spark RDDs SparkConf conf = new
		 * SparkConf().setAppName("Spark Program").setMaster("local"); JavaSparkContext
		 * sc = new JavaSparkContext(conf);
		 */
		

		Dataset<Row> ds1 = Transformations.jsonDatasetOriginal(spark,
				context.getProperty(GlobalConstants.ReadyJsonFile));
		
		
		ExpressionEncoder<Row> encoder = RowEncoder.apply(Jsonreadystructure.SCHEMA);
		
		
		Dataset<Row> ds1_populationGt100k = ds1.filter(ds1.col("countryPopulation").cast("int").$greater(140000000)).alias("ds1_population");

		Dataset<Row> ds1_countryNameContain_ia = ds1.filter(ds1.col("countryName").contains("ia")).alias("ds1_country");

		Dataset<Row> ds2_populationGt100k_with_ia = ds1_populationGt100k.join(ds1_countryNameContain_ia,
				ds1_populationGt100k.col("countryName").equalTo(ds1_countryNameContain_ia.col("countryName")));

		Dataset<Row> ds2_populationGt100k_with_ia_filters_only = ds1_populationGt100k.filter(ds1_populationGt100k.col("countryName").contains("ia"));

		ds1_populationGt100k.show(5);
		ds1_countryNameContain_ia.show(5);


		ds2_populationGt100k_with_ia.show();

		ds2_populationGt100k_with_ia_filters_only.show();


		// Filter Records
//		Dataset<Row> ds2 = ds1.filter(ds1.col("countryName").contains("a"));
//		ds2.createOrReplaceTempView("country_name_ds2");
//		ds2.show();
//
//		Dataset<Row> ds3 = ds1.filter(ds1.col("countryPopulation").cast("int").$greater(100000)).join(ds2,
//				ds1.col("countrySize").cast(DataTypes.IntegerType).alias("a1")
//						.equalTo(ds2.col("countrySize").cast(DataTypes.IntegerType).alias("a2")),
//				"INNER");
//
//		ds3.show();
//
		
		
		
		
		
		
		
		
		
		
		
		

//			Dataset<Row> ds5 = spark.sql(
//					"SELECT country_data.countryName, country_data.countryPopulation, country_data.countrySize from country_data INNER JOIN  country_name_ds2 ON country_data.countrySize = country_name_ds2.countrySize");
//			ds5.show();

		// Join Ds1 & Ds2

		/*
		 * Dataset<Row> ds4 = ds1.map(new MapFunction<Row, Row>() {
		 * 
		 * @Override public Row call(Row row) {
		 * 
		 * if (Integer.valueOf(row.get(1).toString()) > 100000) {
		 * 
		 * return row; }
		 * 
		 * }
		 * 
		 * }, Encoders.INT()).join(ds2,
		 * ds1.col("countrySize").cast(DataTypes.IntegerType).alias("a1")
		 * .equalTo(ds2.col("countrySize").cast(DataTypes.IntegerType).alias("a2")));
		 * 
		 * /* , * List<String> lists = new ArrayList<String>();
		 * ds1.foreach((ForeachFunction<Row>) row -> lists.add(row.toString()));
		 * Dataset<String> df3 = spark.createDataset(lists, Encoders.STRING());
		 * df3.show();
		 */

	}

}
