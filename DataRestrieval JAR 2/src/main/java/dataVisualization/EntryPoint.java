package dataVisualization;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class EntryPoint {

	public void getObj(AmazonS3 s3client) {

		// start retrieve file
		String bucketName = S3Constants.BUCKET_NAME;
		String objectName = S3Constants.BUCKET_FILE_PATH;

		try {
			S3Object s3object = s3client.getObject(bucketName, objectName);
			S3ObjectInputStream inputStream = s3object.getObjectContent();
			FileUtils.copyInputStreamToFile(inputStream, new File(S3Constants.LOCAL_DOWNLOAD_PATH));

			System.out.println("file copied to destination.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
