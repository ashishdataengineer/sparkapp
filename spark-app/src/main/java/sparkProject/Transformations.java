package sparkProject;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Transformations {
	
	
	
	public static Dataset<Row> jsonDatasetOriginal(SparkSession spark, String JsonPath) {

		Dataset<Row> ds = spark.read().schema(Jsonreadystructure.SCHEMA)
				.json(JsonPath);

		ds.createOrReplaceTempView("country_data");
		return spark.sql(
				"SELECT country.name as countryName, country.population as countryPopulation, country.area as countrySize  FROM country_data lateral view explode(countries.country) t as country");

	}

}
