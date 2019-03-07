package com.FaceReko.credentials;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;

public class Credentials {

    public static AmazonRekognition getClient(){
    AWSCredentials credentials= new ProfileCredentialsProvider().getCredentials();

    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
            .withRegion(Regions.AP_SOUTH_1)
            .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    return rekognitionClient;
    }
}
