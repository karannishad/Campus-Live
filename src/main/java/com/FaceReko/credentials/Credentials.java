package com.FaceReko.credentials;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class Credentials {

    public static AmazonRekognition getClient(){
    AWSCredentials credentials= new ProfileCredentialsProvider().getCredentials();

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
            .withRegion(Regions.AP_SOUTH_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    return rekognitionClient;
    }

    public static AmazonS3 getS3Client(){
        AWSCredentials credentials= new ProfileCredentialsProvider().getCredentials();

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        return s3;
    }
}
