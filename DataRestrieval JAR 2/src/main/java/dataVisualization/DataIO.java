package dataVisualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.List;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class DataIO {
	private String inputCSV;
	private ArrayList<Double> x = new ArrayList<Double>(0);
	private ArrayList<Double> y = new ArrayList<Double>(0);
	// want to access from another class, don't want new instance (plotter)
	static String xTitle;
	static String yTitle;
	String bucketName = "project-2-group-3-bucket-cbpc";

	// need another class to extract S3 bucket CSV String location
	public DataIO(String inputCSV) {
		this.inputCSV = inputCSV;
	}

	// Input from hardcoded local CSV
	public void CSVList() {
		File file = new File(this.inputCSV);
		int i = 0;
		Scanner inputStream;
		try {
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String line = inputStream.next();
				String[] values = line.split(",");
				if (i == 0) {
					DataIO.xTitle = values[0].toString();
					DataIO.yTitle = values[1].toString();
					i++;
				} else { // this adds the currently parsed line to the 2-dimensional string array
					Double temp = Double.valueOf(values[0]);
					Double temp2 = Double.valueOf(values[1]);
					this.x.add(temp);
					this.y.add(temp2); // issue
				}
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Input from S3 Bucket
	 */
	public void S3List(String csvName, String aKey, String sKey) {
		AWSCredentials credentials = new BasicAWSCredentials(aKey, sKey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_EAST_2).build();

		if (s3client.doesBucketExist(bucketName)) {
			System.out.println("Bucket name is not available." + "Try again with a different Bucket name.");
			return;
		}

		List<Bucket> buckets = s3client.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.getName());
		}

		ObjectListing objectListing = s3client.listObjects("project-2-group-3-bucket-cpbc");
		for (S3ObjectSummary os : objectListing.getObjectSummaries()) {
			System.out.println(os.getKey());
		}

		S3Object fullObject = null;
		String key = "Output/hello.csv/" + csvName; /////////////////
		System.out.println("key = " + key);
		System.out.println("Downloading an object");
		fullObject = s3client.getObject(new GetObjectRequest("project-2-group-3-bucket-cpbc", key));
		System.out.println("Content-Type: " + fullObject.getObjectMetadata().getContentType());
		System.out.println("Content: ");
		System.out.println(fullObject.getObjectContent());

		BufferedReader reader = new BufferedReader(new InputStreamReader(fullObject.getObjectContent()));
		String line;
		int count = 0;
		try {
			while ((line = reader.readLine()) != null) {
				//System.out.println(line); /// *****STORE TO ARRAYLIST DOUBLE*****
				String[] values = line.split(",");
				if( count ==0 ) {
					DataIO.xTitle = values[0].toString();
					DataIO.yTitle = values[1].toString();
					count++;
				}else {
					Double temp = Double.valueOf(values[0]);
					Double temp2 = Double.valueOf(values[1]);
					this.x.add(temp);
					this.y.add(temp2);
				}
				count++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Double> getX() {
		return this.x;
	}

	public void setX(ArrayList<Double> x) {
		this.x = x;
	}

	public ArrayList<Double> getY() {
		return this.y;
	}

	public void setY(ArrayList<Double> y) {
		this.y = y;
	}

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public String getyTitle() {
		return yTitle;
	}

	public void setyTitle(String yTitle) {
		this.yTitle = yTitle;
	}

}
