package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class CompareFaces {
    public static void main(String[] args) throws Exception{
        Float similarityThreshold = 70F;
       // String sourceImage = "source.jpg";
        String targetImage = "/home/consultadd/Downloads/index.jpeg";
       // ByteBuffer sourceImageBytes=null;
        ByteBuffer targetImageBytes=null;

        AmazonRekognition rekognitionClient = Credentials.getClient();

        //Load source and target images and create input parameters
//        try (InputStream inputStream = new FileInputStream(new File(sourceImage))) {
//            sourceImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
//        }
//        catch(Exception e)
//        {
//            System.out.println("Failed to load source image " + sourceImage);
//            System.exit(1);
//        }
        try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load target images: " + targetImage);
            System.exit(1);
        }
//
//        Image source=new Image()
//                .withBytes(sourceImageBytes);
        Image target=new Image()
                .withBytes(targetImageBytes);
    for(int i=1;i<3;i++){
        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(new Image().withS3Object(new S3Object()
                        .withBucket("imagefacereko")
                        .withName(i+".jpeg")))
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
        CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);

    // Display results
        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        for (CompareFacesMatch match: faceDetails){

            ComparedFace face= match.getFace();
            BoundingBox position = face.getBoundingBox();
            System.out.println("Face at " + position.getLeft().toString()
                    + " " + position.getTop()
                    + " matches with " + match.getSimilarity().toString()
                    + "% confidence."+ match.toString());

        }
//        List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();
//
//        System.out.println("There was " + uncompared.size()
//                + " face(s) that did not match");
       }
    }
}
