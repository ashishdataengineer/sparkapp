package sparkProject;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.ForeachWriter;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.catalyst.encoders.ExpressionEncoder;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.execution.datasources.jdbc.JDBCOptions;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.streaming.Trigger;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

public class SparkStreamingDBInsert {

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

	public static void main(String[] args) throws StreamingQueryException, InterruptedException {

		SparkConf conf = new SparkConf();
		SparkSession spark = SparkSession.builder().config(conf).appName("Spark Program").master("local[*]")
				.getOrCreate();

		Dataset<Row> ds1 = spark.readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
				.option("subscribe", "Kafkademo").load();

		Dataset<Row> ss = ds1.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)");
		ss.printSchema();

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
		
		
	/*	StreamingQuery query = finalOP
                .writeStream()
                .outputMode("append")
                .format("streaming-jdbc")
                .outputMode(OutputMode.Append())
                .option(JDBCOptions.JDBC_URL(), "cjdbc:mysql://localhost:3306")
                .option(JDBCOptions.JDBC_TABLE_NAME(), "sys.Employee")
                .option(JDBCOptions.JDBC_DRIVER_CLASS(), "com.mysql.cj.jdbc.Driverr")
                .option(JDBCOptions.JDBC_BATCH_INSERT_SIZE(), "5")
                .option("user", "user")
                .option("password", "ashu@123")
                .trigger(Trigger.ProcessingTime("10 seconds"))
                .start();
        query.awaitTermination();*/

		StreamingQuery query  = finalOP.writeStream().foreach(new ForeachWriter<Row>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void process(Row value) {

				try {
					String myDriver = "com.mysql.cj.jdbc.Driver";
					Class.forName(myDriver);
					String myUrl = "jdbc:mysql://localhost:3306";
					Connection conn = DriverManager.getConnection(myUrl, "root", "ashu@123");
					

					String query = " insert into sys.Employee (FirstName, LastName, Title, ID, Division, Supervisor) values (?, ?, ?, ?, ?, ?)";
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, value.getAs("FirstName"));
					preparedStmt.setString(2, value.getAs("LastName"));
					preparedStmt.setString(3, value.getAs("Title"));
					preparedStmt.setInt(4, Integer. valueOf(value.getAs("ID")));
					preparedStmt.setString(5, value.getAs("Division"));
					preparedStmt.setString(6, value.getAs("Supervisor"));

					preparedStmt.execute();
					conn.commit();
					System.out.println("COMMITTED ---------------------------------------------");

					conn.close();

				} catch (ClassNotFoundException | SQLException e) {
					
					e.printStackTrace();
				}

			}

			@Override
			public void close(Throwable errorOrNull) {
				

			}

			@Override
			public boolean open(long partitionId, long epochId) {
				
				return false;
			}
		}).start();
		
		query.awaitTermination();
		System.out.println("SHOW SCHEMA");

	}

}
