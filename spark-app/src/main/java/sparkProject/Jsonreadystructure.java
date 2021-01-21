package sparkProject;

import org.apache.spark.sql.types.StructType;

import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;

public class Jsonreadystructure {

	public static final StructType SCHEMA = new StructType(new StructField[] {

			DataTypes.createStructField("countries", new StructType().add(DataTypes.createStructField("country",
					new ArrayType(new StructType().add(DataTypes.createStructField("name", DataTypes.StringType, true))
							.add(DataTypes.createStructField("population", DataTypes.StringType, true))
							.add(DataTypes.createStructField("area", DataTypes.StringType, true)), true),
					true)), true)

	}

	);

	public static final StructType SCHEMACSV = new StructType(new StructField[] {

			new StructField("policyID", DataTypes.StringType, false, Metadata.empty()),
			new StructField("statecode", DataTypes.StringType, false, Metadata.empty()),
			new StructField("county", DataTypes.StringType, false, Metadata.empty()),
			new StructField("eq_site_limit", DataTypes.StringType, false, Metadata.empty()),
			new StructField("hu_site_limit", DataTypes.StringType, false, Metadata.empty()),
			new StructField("fl_site_limit", DataTypes.StringType, false, Metadata.empty()),
			new StructField("fr_site_limit", DataTypes.StringType, false, Metadata.empty()),
			new StructField("tiv_2011", DataTypes.StringType, false, Metadata.empty()),
			new StructField("tiv_2012", DataTypes.StringType, false, Metadata.empty()),
			new StructField("eq_site_deductible", DataTypes.StringType, false, Metadata.empty()),
			new StructField("hu_site_deductible", DataTypes.StringType, false, Metadata.empty()),
			new StructField("fl_site_deductible", DataTypes.StringType, false, Metadata.empty()),
			new StructField("fr_site_deductible", DataTypes.StringType, false, Metadata.empty()),
			new StructField("point_latitude", DataTypes.StringType, false, Metadata.empty()),
			new StructField("point_longitude", DataTypes.StringType, false, Metadata.empty()),
			new StructField("line", DataTypes.StringType, false, Metadata.empty()),
			new StructField("construction", DataTypes.StringType, false, Metadata.empty()),
			new StructField("point_granularity", DataTypes.StringType, false, Metadata.empty())

	}

	);

}
