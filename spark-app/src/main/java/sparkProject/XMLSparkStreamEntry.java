package sparkProject;


import java.io.StringReader;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

public class XMLSparkStreamEntry {

	static StructType structType = new StructType();

	static {
		structType = structType.add("FirstName", DataTypes.StringType, false);
		structType = structType.add("LastName", DataTypes.StringType, false);
		structType = structType.add("Title", DataTypes.StringType, false);
		structType = structType.add("ID", DataTypes.StringType, false);
		structType = structType.add("Division", DataTypes.StringType, false);
		structType = structType.add("Supervisor", DataTypes.StringType, false);

	}

	static ExpressionEncoder<Row> encoder = RowEncoder.apply(structType);

	public static void main(String[] args) throws StreamingQueryException {

		SparkConf conf = new SparkConf();
		SparkSession spark = SparkSession.builder().config(conf).appName("Spark Program").master("local[*]")
				.getOrCreate();

		Dataset<Row> ds1 = spark.readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
				.option("subscribe", "Kafkademo").load();

		Dataset<Row> ss = ds1.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");

		Dataset<Row> finalOP = ss.flatMap(new FlatMapFunction<Row, Row>() {

			private static final long serialVersionUID = 1L;

			@Override
			public Iterator<Row> call(Row t) throws Exception {

				JAXBContext jaxbContext = JAXBContext.newInstance(FileWrapper.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

				StringReader reader = new StringReader(t.getAs("value"));
				FileWrapper person = (FileWrapper) unmarshaller.unmarshal(reader);

				List<Employee> emp = new ArrayList<Employee>(person.getEmployees());
				List<Row> rows = new ArrayList<Row>();
				for (Employee e : emp) {

					rows.add(RowFactory.create(e.getFirstname(), e.getLastname(), e.getTitle(), e.getId(),
							e.getDivision(), e.getSupervisor()));

				}
				return rows.iterator();
			}
		}, encoder);

		Dataset<Row> wordCounts = finalOP.groupBy("FirstName").count();
		StreamingQuery query = wordCounts.writeStream().outputMode("complete").format("console").start();
		System.out.println("SHOW SCHEMA");
		query.awaitTermination();

	}

}
