package com.github.p2g3.s3testing.startup;

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
                String aKey = "";
                String sKey = "";

            this.sparkSession = SparkSession
                .builder()
                .master("local")
                .appName("spark")
                .config("fs.s3a.access.key", aKey)
                .config("fs.s3a.secret.key", sKey)
                .getOrCreate();
                System.out.println("CREATING SESSION!!!");

                /*sparkSession.sparkContext().setLogLevel("WARN");
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.endpoint", "s3-us-east-2.amazonaws.com");
<<<<<<< HEAD
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.access.key", "ENTER KEY");
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.secret.key", "ENTER KEY");
                sparkSession.sparkContext().hadoopConfiguration().addResource("conf.xml");
=======
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.access.key", "");
                sparkSession.sparkContext().hadoopConfiguration().set("fs.s3a.secret.key", "");*/
>>>>>>> aa41ec9646fe94f5fe16714e449a229e6e6bc56e
            }
    }
    
    public SparkSession getSession(){
        return this.sparkSession;
    }
}