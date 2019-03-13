package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class AddFacesToCollection {
    public static final String collectionId = "MyCollection";
    public static void main(String[] args) throws Exception {
        String photo = "/home/consultadd/Downloads/3.jpeg";
        AmazonRekognition amazonRekognition= Credentials.getClient();
        ByteBuffer imagebyte;
        try(InputStream inputStream =new FileInputStream(new File(photo)) ){
            imagebyte =ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        Image image = new Image()
               .withBytes(imagebyte);

        IndexFacesRequest indexFacesRequest = new IndexFacesRequest()
                .withImage(image)
                .withExternalImageId("OneD")
                .withMaxFaces(10)
                .withCollectionId(collectionId)
                .withDetectionAttributes("ALL");

        IndexFacesResult indexFacesResult = amazonRekognition.indexFaces(indexFacesRequest);

        System.out.println("Results for " + photo);
        System.out.println("Faces indexed:");


        List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();
        for (FaceRecord faceRecord : faceRecords) {
            System.out.println("  Face ID: " + faceRecord.getFace().getFaceId());
            System.out.println("  External Face ID: " + faceRecord.getFace().getExternalImageId());

            System.out.println("  Location:" + faceRecord.getFaceDetail().getBoundingBox().toString());

            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(faceRecord));
        }

        List<UnindexedFace> unindexedFaces = indexFacesResult.getUnindexedFaces();
        System.out.println("Faces not indexed:");
        for (UnindexedFace unindexedFace : unindexedFaces) {
            System.out.println("  Location:" + unindexedFace.getFaceDetail().getBoundingBox().toString());
            System.out.println("  Reasons:");
            for (String reason : unindexedFace.getReasons()) {
                System.out.println("   " + reason);
            }
        }
    }
}
