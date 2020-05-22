package com.github.p2group3.startup;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class CreateSparkSession {
    private SparkSession sparkSession;
    private static CreateSparkSession instance;

    //creates a singleton
    private CreateSparkSession(){}
    public static CreateSparkSession getInstance(){
        if (instance == null){
            instance = new CreateSparkSession();
            instance.createSession();
        }
        return instance;
    }

    private void createSession(){
            // create session to load csv
            if (this.sparkSession == null){
            this.sparkSession = SparkSession
                .builder()
                .master("local[*]")
                .appName("spark")
                .getOrCreate();
                System.out.println("CREATING SESSION!!!");

                sparkSession.sparkContext().setLogLevel("WARN");
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.endpoint", "s3-us-east-2.amazonaws.com");
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.access.key", "AKIAJZDHCBVU4JKIJRSA");
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.secret.key", "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");
                sparkSession.sparkContext().hadoopConfiguration().addResource("conf.xml");
                
            }
    }
    
    public SparkSession getSession(){
        return this.sparkSession;
    }
}