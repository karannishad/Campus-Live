package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class CompareFaces {
    public static void main(String[] args) throws Exception{
        Float similarityThreshold = 70F;
        String targetImage = "/home/consultadd/Downloads/index.jpeg";
        ByteBuffer targetImageBytes=null;
        String ResultsPrint=null;
        AmazonRekognition rekognitionClient = Credentials.getClient();
        AmazonS3 s3= Credentials.getS3Client();


        try (InputStream inputStream = new FileInputStream(new File(targetImage))) {
            targetImageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            System.out.println("Failed to load target images: " + targetImage);
            System.exit(1);
        }

        Image target=new Image()
                .withBytes(targetImageBytes);

        ListObjectsV2Result result = s3.listObjectsV2("imagefacereko");
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary os: objects) {
        CompareFacesRequest request = new CompareFacesRequest()
                .withSourceImage(new Image().withS3Object(new S3Object()
                        .withBucket("imagefacereko")
                        .withName(os.getKey())))
                .withTargetImage(target)
                .withSimilarityThreshold(similarityThreshold);

        // Call operation
                 CompareFacesResult compareFacesResult=rekognitionClient.compareFaces(request);

    // Display results
                 List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
            for (CompareFacesMatch match: faceDetails){
                    ResultsPrint+=os.getKey();
//            ComparedFace face= match.getFace();
//            BoundingBox position = face.getBoundingBox();
//            System.out.println("Face at " + position.getLeft().toString()
//                    + " " + position.getTop()
//                    + " matches with " + match.getSimilarity().toString()
//                    + "% confidence."+ match.toString());

             }
//             List<ComparedFace> uncompared = compareFacesResult.getUnmatchedFaces();
//
//            System.out.println("There was " + uncompared.size()
//                + " face(s) that did not match");
//       }
    }
        System.out.println(ResultsPrint);
    }

}
