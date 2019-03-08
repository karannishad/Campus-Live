package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.Face;
import com.amazonaws.services.rekognition.model.ListFacesRequest;
import com.amazonaws.services.rekognition.model.ListFacesResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ListFacesInCollection {
    public static final String collectionId = "MyCollection";

    public static void main(String[] args) throws Exception {

        AmazonRekognition rekognitionClient = Credentials.getClient();

        ObjectMapper objectMapper = new ObjectMapper();

        ListFacesResult listFacesResult = null;
        System.out.println("Faces in collection " + collectionId);

        String paginationToken = null;
        do {
            if (listFacesResult != null) {
                paginationToken = listFacesResult.getNextToken();
            }

            ListFacesRequest listFacesRequest = new ListFacesRequest()
                    .withCollectionId(collectionId)
                    .withNextToken(paginationToken);

            listFacesResult =  rekognitionClient.listFaces(listFacesRequest);
            List<Face> faces = listFacesResult.getFaces();
            for (Face face: faces) {
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(face));
            }
        } while (listFacesResult != null && listFacesResult.getNextToken() !=
                null);
    }
}
