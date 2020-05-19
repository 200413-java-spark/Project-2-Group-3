package com.github.p2group3.startup;

import org.apache.spark.sql.SparkSession;

public class CreateSparkSession {
    private SparkSession sparkSession;
    private static CreateSparkSession instance;

    //creates a singleton
    public CreateSparkSession(){}
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
                .master("local")
                .appName("spark")
                .getOrCreate();
                System.out.println("CREATING SESSION!!!");
            }
    }
    
    public SparkSession getSession(){
        return this.sparkSession;
    }
}