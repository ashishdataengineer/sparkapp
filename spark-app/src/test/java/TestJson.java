import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

import sparkProject.Jsonreadystructure;


public class TestJson {
	
	
	static String jsonPath = "/home/ashish/TransformedJSON.json";
	
	@Test
	public void testJson() {
		
		SparkSession spark = SparkSession.builder().appName("Spark Program").master("local").getOrCreate();
		Dataset<Row> ds = spark.read().schema(Jsonreadystructure.SCHEMA)
				.json(jsonPath);
		
		ds.createOrReplaceTempView("country_data");
		Dataset<Row> df = spark.sql("SELECT country.name FROM country_data lateral view explode(countries.country) t as country"); 
		df.show();
		
	}
	

}
