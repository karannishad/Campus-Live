package com.FaceReko.Working;
//Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
//PDX-License-Identifier: MIT-0 (For details, see https://github.com/awsdocs/amazon-rekognition-developer-guide/blob/master/LICENSE-SAMPLECODE.)



import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;


public class DetectFaces {


 public static void main(String[] args) throws Exception {

    String photo = "/home/consultadd/Downloads/index.jpeg";
    //String bucket = "elasticbeanstalk-ap-south-1-312872316571";
     AWSCredentials credentials= new ProfileCredentialsProvider().getCredentials();

//    try {
//		credentials= new ProfileCredentialsProvider().getCredentials();
//	} catch (Exception e) {
//		// TODO: handle exception
//	}
//    
    ByteBuffer imagebyte;
    try(InputStream inputStream =new FileInputStream(new File(photo)) ){
    	imagebyte =ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
    }
    AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.standard()
    		.withRegion(Regions.AP_SOUTH_1)
    		.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

    DetectFacesRequest request =new DetectFacesRequest()
    		.withImage(new Image().withBytes(imagebyte))
    		.withAttributes(Attribute.ALL);
    
    try {
        DetectFacesResult result = rekognitionClient.detectFaces(request);
        List < FaceDetail > faceDetails = result.getFaceDetails();

        for (FaceDetail face: faceDetails) {
        	Pose pose=face.getPose();

        		Gender gen=face.getGender();
              AgeRange ageRange = face.getAgeRange();
              System.out.println("pserson pose is "+pose.toString());
              System.out.println("person is " +gen.getValue());
              System.out.println("The detected face is estimated to be between "
                 + ageRange.getLow().toString() + " and " + ageRange.getHigh().toString()
                 + " years old.");
           
         

           ObjectMapper objectMapper = new ObjectMapper();
           System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(face));
        }

     } catch (AmazonRekognitionException e) {
        e.printStackTrace();
     }

//
//    detectFacesRequest request = new detectFacesRequest()
//       .withImage(new Image()
//        .withBytes(imagebyte))
//          .withMaxLabels(10).withMinConfidence(55F);
//
//    try {
//       detectFacesResult result = rekognitionClient.detectFaces(request);
//       List < Label > labels = result.getLabels();
//
//       for (Label label:labels) {
//        System.out.println(label.getName()+" "+label.getConfidence().toString());
//       }
//
//    } catch (AmazonRekognitionException e) {
//       e.printStackTrace();
//    }

 }

}

