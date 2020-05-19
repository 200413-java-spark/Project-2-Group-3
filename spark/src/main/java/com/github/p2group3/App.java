package com.github.p2group3;

import com.github.p2group3.io.LoadCSV;
import com.github.p2group3.startup.CreateSparkSession;

import org.apache.catalina.LifecycleException;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws LifecycleException, AnalysisException
    {
        CreateSparkSession.getInstance(); 
        SparkSession session = new CreateSparkSession().getSession(); //starts SparkSession
        String fileName = "vgsales-12-4-2019-short.csv";
        Dataset<Row> data = new LoadCSV().getCSVFileSession(fileName);

        data.printSchema();

        data.createGlobalTempView("salesshort");

        session.sql("SELECT * FROM global_temp.salesshort").show();
        //session.sql("WHERE Publisher=\'Nintendo\'").show();
    }
}
