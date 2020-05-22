package com.github.p2g3.s3testing;

import java.io.IOException;

import com.github.p2g3.s3testing.startup.CreateSparkSession;
import com.github.p2g3.s3testing.startup.FileParser;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class S3Test3 {
    public static void main(String[] args) throws IOException
    {  
        CreateSparkSession startSession = CreateSparkSession.getInstance(); //Starts SparkSession
        SparkSession session = startSession.getSession(); //pulls a reference to the session

        Dataset<Row> data = new FileParser().parseFile();
        data.printSchema();
        //new Operations().runOperations(data, session);

        
//spark.sparkContext().hadoopConfiguration().addResource("conf.xml");

    }
}