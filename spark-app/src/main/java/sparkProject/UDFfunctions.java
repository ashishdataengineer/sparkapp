package sparkProject;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

public class UDFfunctions {

	public static void registerCountryCodeFunction(SparkSession spark) {

		spark.udf().register("registerCountryCodeFunctionUDF", new UDF1<String, Integer>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Integer call(String t1) throws Exception {
				if (t1.toString().toUpperCase().startsWith("I")) {

					return 01;
				}
				return 02;
			}

		}, DataTypes.IntegerType);

	}

	public static void registerPrintValue(SparkSession spark) {

		spark.udf().register("registerPrintValue", new UDF1<String, String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String call(String t1) throws Exception {

				System.out.println("Value: " + t1);
				return t1;
			}

		}, DataTypes.StringType);

	}

}
