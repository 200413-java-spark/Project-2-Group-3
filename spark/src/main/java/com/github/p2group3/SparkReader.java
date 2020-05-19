package com.github.p2group3;

import java.util.function.ToDoubleFunction;

import com.github.p2group3.io.LoadCSV;
import com.github.p2group3.startup.CreateSparkSession;

import org.apache.catalina.LifecycleException;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.DoubleType;

/**
 * Hello world!
 *
 */
public class SparkReader {
    public static void main(String[] args) throws LifecycleException, AnalysisException
    {
        CreateSparkSession startSession = CreateSparkSession.getInstance(); //Starts SparkSession
        SparkSession session = startSession.getSession(); //pulls a reference to the session

        String fileName = "vgsales-12-4-2019.csv";
        //String fileName = "sample.csv";
        Dataset<Row> data = new LoadCSV().getCSVFileSession(fileName).cache();

        data.printSchema();

        //data.createOrReplaceTempView("salesshort");

        //data.show();

        Dataset<Row> saleDataDouble1 = data.withColumn("Global_Sales", data.col("Global_Sales").cast(DataTypes.DoubleType));
        Dataset<Row> saleDataDouble = saleDataDouble1.withColumn("Year", data.col("Year").cast(DataTypes.IntegerType));
        saleDataDouble.createOrReplaceTempView("salesshort");

        //session.sql("SELECT * FROM salesshort WHERE Publisher=\'Nintendo\'").show();
        //session.sql("SELECT Name, Publisher, Global_Sales FROM salesshort WHERE Publisher=\'Nintendo\'").show();

        /*Dataset<Row> allGenresSalesDF = session.sql("SELECT Genre, Global_Sales, Year FROM salesshort").na().drop();//drops null values using .na().drop() from table
        allGenresSalesDF.show();
        allGenresSalesDF.createOrReplaceTempView("genretable");
        session.sql("SELECT Genre ,COUNT(*) AS Total_Sales FROM genretable GROUP BY Genre").show();*/

        /*Dataset<Row> actionSalesDF = session.sql("SELECT Global_Sales, Year FROM salesshort WHERE Genre=\'Action\'").na().drop().cache();
        actionSalesDF.show();
        actionSalesDF.createOrReplaceTempView("actionGTable");
        //Dataset<Row> yearActSold = 
        Dataset<Row> yearGSales = session.sql("SELECT Year ,ROUND(SUM(Global_Sales),2) AS Total_Sales FROM actionGTable GROUP BY Year");
        yearGSales.createOrReplaceTempView("ysTable");
        session.sql("SELECT * FROM ysTable ORDER BY Year").show(50);*/



    }
}