package com.github.p2group3;

import java.util.ArrayList;
import java.util.List;

import com.github.p2group3.io.LoadCSV;
import com.github.p2group3.startup.CreateSparkSession;

import org.apache.catalina.LifecycleException;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
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
        //reads in CSV data
    
        Dataset<Row> data = new LoadCSV().getCSVFileSession();
        //data.printSchema();
        //drops rows that are not important
        Dataset<Row> data2 = data.drop(data
        .col("Last_Update")).drop(data.col("url"))
        .drop(data.col("status")).drop(data
        .col("img_url"));

        data2.createOrReplaceTempView("dataset0");
        //data2.show();
        String[] headers = data2.columns();
        for (int i = 0; i < headers.length; i++){
            System.out.print(i+" ");
            System.out.println(headers[i]);
        }

        int firstH = 3;
        int seconH = 12;

        Dataset<Row> selectedCategoryPre = session.sql("SELECT "+headers[firstH]+", "
            +headers[seconH]+", Year FROM dataset0").na().drop();//drops null values using .na().drop() from table

        Dataset<Row> selectedCategory = selectedCategoryPre.withColumn(headers[seconH], 
            selectedCategoryPre.col(headers[seconH]).cast(DataTypes.DoubleType)).cache();

        //selectedCategory.show();
        selectedCategory.createOrReplaceTempView("newtable");

        Dataset<Row> sCCount = session.sql("SELECT "+headers[firstH]+
            " ,COUNT(*) AS New_Column FROM newtable GROUP BY "+headers[firstH]).cache();
        sCCount.createOrReplaceTempView("tableOfCategories");

        int selectCount = (int) sCCount.count();
        //System.out.println(selectCount);
        //sCCount.show();
        List<Row> list1 = new ArrayList<>();
        list1 = sCCount.select(headers[firstH]).takeAsList(selectCount);

        //list1.get(0).get(0).toString();

        String option1 = "SUM";
        String option2 = "AVE";

        List<Dataset<Row>> addList = new ArrayList<>();

        for (int i =0; i< selectCount; i++){
            //System.out.println(list1.get(i).get(0).toString());

            Dataset<Row> pickTable = session.sql("SELECT "+headers[seconH]+
            ", Year FROM newtable WHERE "+headers[firstH]+
            "=\'"+list1.get(i).get(0).toString()+"\'").na().drop().cache();
            pickTable.createOrReplaceTempView("pickTable");
            Dataset<Row> yearSales = session.sql("SELECT Year ,ROUND("+option1+"("+headers[seconH]+"),2) AS Totals FROM pickTable GROUP BY Year");
            yearSales.createOrReplaceTempView("ysTable");
            Dataset<Row> createdYearsTables = session.sql("SELECT * FROM ysTable ORDER BY Year");
            addList.add(createdYearsTables);
        }

        int flag1 = 0;
        while(flag1 ==0){
            for (int i =0; i< selectCount; i++){
                System.out.print(i + "-");
                System.out.println(list1.get(i).get(0).toString());}

            System.out.print("Pick Row to display: ");
            int choice = Integer.parseInt(System.console().readLine());

            if (choice == -1) {
                flag1 = 1;
            }
            else{
                addList.get(choice).show(50);
                System.out.println(list1.get(choice).get(0).toString());
            }
        }
        //list1.forEach(x -> System.out.println(x.get(0).toString()));
        /*
        Dataset<Row> data2Cloned = data2.toDF(headers[0],headers[1],headers[2],
                headers[3],headers[4],headers[5],
                headers[6],headers[7],headers[8],
                headers[9],headers[10],headers[11],
                headers[12],headers[13],headers[14],
                headers[15],headers[16],headers[17],
                headers[18]);*/

        //Dataset<Row> colTest2 = data2.select(functions.coalesce(data2.col("Total_Shipped"),data2.col("Global_Sales"))).toDF("Global_Sales");
        //Dataset<Row> colTest3 = colTest2.toDF("TG_Sales");

        //Dataset<Row> data3 = data2Cloned.withColumn("Global_Sales", colTest3.col("TG_Sales"));

        //Dataset<Row> data3 = data2.drop("Total_Shipped").drop("Global_Sales").withColumn("Total_Global_Sales", data2.col("Total_Shipped").cast(DataTypes.DoubleType));
        //data3.printSchema();
        //data3.show();
        //test code block 2
        /*
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
        salesCol2.show();*/
        /*
        Dataset<Row> saleDataDouble2 = data.withColumn("Total_Shipped", 
            data.col("Total_Shipped").cast(DataTypes.DoubleType));
        Dataset<Row> saleDataDouble1 = saleDataDouble2.withColumn("Global_Sales", 
            data.col("Global_Sales").cast(DataTypes.DoubleType));
        Dataset<Row> saleDataDouble = saleDataDouble1.withColumn("Year", 
            data.col("Year").cast(DataTypes.IntegerType));
        saleDataDouble.createOrReplaceTempView("salesshort");
        */

        /*
        Dataset<Row> data3 = data2.withColumn("Total_Global_Sales2", testCol);
        data3.createOrReplaceTempView("dataset1");*/
        
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