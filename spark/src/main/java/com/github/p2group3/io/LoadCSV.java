package com.github.p2group3.io;

import com.github.p2group3.startup.CreateSparkSession;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class LoadCSV {
    CreateSparkSession session = CreateSparkSession.getInstance();
    
    public Dataset<Row> getCSVFileSession(){
        
        SparkSession sparkSession = this.session.getSession();
        if (sparkSession == null){
            System.out.println("SPARKSESSION CODE FAILURE!!!");
        }

        Dataset<Row> dataCSV = sparkSession.read().format("csv").option("header", "true")
                .load("s3a://project-2-group-3-bucket-cpbc/Input/vgsales-12-4-2019-short.csv");   

        return dataCSV;
    }
}