package com.FaceReko.S3;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.List;

public class RetriveImage {
    public static void main(String[] args) {
        AmazonRekognition amazonRekognition= Credentials.getClient();

        AmazonS3 s3= Credentials.getS3Client();
        for (Bucket bucket:s3.listBuckets()) {

            System.out.println( bucket.getName());

        }
        ListObjectsV2Result result = s3.listObjectsV2("imagefacereko");
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os: objects) {
            System.out.println("* " + os.getKey());
        }
    }
}
