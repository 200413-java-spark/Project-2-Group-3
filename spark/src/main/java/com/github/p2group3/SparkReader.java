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
public class SparkReader {
    public static void main(String[] args) throws LifecycleException, AnalysisException
    {
        CreateSparkSession startSession = CreateSparkSession.getInstance(); //Starts SparkSession
        SparkSession session = startSession.getSession(); //pulls a reference to the session
        if (session != null){
            System.out.println("A SESSION DOES INDEED EXIST!!!");
        }
        String fileName = "vgsales-12-4-2019-short.csv";
        //String fileName = "sample.csv";
        Dataset<Row> data = new LoadCSV().getCSVFileSession(fileName);

        data.printSchema();

        data.createOrReplaceTempView("salesshort");

        //data.show();

        session.sql("SELECT * FROM salesshort WHERE Publisher=\'Nintendo\'").show();
    }
}