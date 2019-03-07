package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.ListCollectionsRequest;
import com.amazonaws.services.rekognition.model.ListCollectionsResult;

import java.util.List;

public class ListCollection {
    public static void main(String[] args) throws Exception {

        AmazonRekognition amazonRekognition= Credentials.getClient();


        System.out.println("Listing collections");
        int limit = 10;
        ListCollectionsResult listCollectionsResult = null;
        String paginationToken = null;
        do {
            if (listCollectionsResult != null) {
                paginationToken = listCollectionsResult.getNextToken();
            }
            ListCollectionsRequest listCollectionsRequest = new ListCollectionsRequest()
                    .withMaxResults(limit)
                    .withNextToken(paginationToken);
            listCollectionsResult=amazonRekognition.listCollections(listCollectionsRequest);

            List< String > collectionIds = listCollectionsResult.getCollectionIds();
            for (String resultId: collectionIds) {
                System.out.println(resultId);
            }
        } while (listCollectionsResult != null && listCollectionsResult.getNextToken() !=
                null);

    }
}
