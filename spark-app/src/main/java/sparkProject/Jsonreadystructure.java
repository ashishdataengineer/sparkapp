package sparkProject;

import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;

public class Jsonreadystructure {

	public static final StructType SCHEMA = new StructType(new StructField[] {

			DataTypes.createStructField("countries",
					new StructType().add(DataTypes.createStructField("country",
							new ArrayType(new StructType()
									.add(DataTypes.createStructField("name", DataTypes.StringType, true)), true),
							true)),
					true) }

	);

}
