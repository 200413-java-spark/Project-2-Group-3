package com.github.p2group3;

import com.github.p2group3.io.LoadCSV;
import com.github.p2group3.startup.CreateSparkSession;

import org.apache.catalina.LifecycleException;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

/**
 * Reads in data from a csv and performs various operations
 *
 */
public class SparkReader {
    public static void main(String[] args) throws LifecycleException, AnalysisException
    {
        CreateSparkSession startSession = CreateSparkSession.getInstance(); //Starts SparkSession
        SparkSession session = startSession.getSession(); //pulls a reference to the session

        String fileName = "vgsales-12-4-2019.csv";
        Dataset<Row> data = new LoadCSV().getCSVFileSession(fileName).cache();
        data.printSchema();

        //test code block 2
        data.createOrReplaceTempView("dataset0");

        Dataset<Row> data2 = data.drop(data
        .col("Last_Update")).drop(data.col("url"))
        .drop(data.col("status")).drop(data
        .col("img_url"));

        data2.show();

        Dataset<Row> salesCol = session.sql("SELECT " +
            "COALESCE(Total_Shipped, Global_Sales) " +
            "FROM dataset0").toDF("Total_Global_Sales"); //combine these two columns
        salesCol.createOrReplaceTempView("salesCol");
        salesCol.show();
        session.sql("SELECT COUNT(*) AS Total_Global_Sales FROM salesCol").show();
        salesCol.printSchema();
        session.sql("SELECT COUNT(*) AS Total_Shipped FROM dataset0").show();
        Column testCol = salesCol.col("Total_Global_Sales")
            .cast(DataTypes.DoubleType);
        Dataset<Row> salesCol2 = salesCol.withColumn("Total_Global_Sales", testCol);
        salesCol2.show();
        /*
        Dataset<Row> saleDataDouble2 = data.withColumn("Total_Shipped", 
            data.col("Total_Shipped").cast(DataTypes.DoubleType));
        Dataset<Row> saleDataDouble1 = saleDataDouble2.withColumn("Global_Sales", 
            data.col("Global_Sales").cast(DataTypes.DoubleType));
        Dataset<Row> saleDataDouble = saleDataDouble1.withColumn("Year", 
            data.col("Year").cast(DataTypes.IntegerType));
        saleDataDouble.createOrReplaceTempView("salesshort");
        */
        Dataset<Row> data3 = data2.withColumn("Total_Global_Sales2", testCol);
        data3.createOrReplaceTempView("dataset1");   
        
        //test code block 1
        //data.createOrReplaceTempView("salesshort");

        //data.show();

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