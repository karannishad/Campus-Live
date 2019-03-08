package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.CreateCollectionRequest;
import com.amazonaws.services.rekognition.model.CreateCollectionResult;

public class CreateCollection {
    public static void main(String[] args) throws Exception {
        AmazonRekognition amazonRekognition= Credentials.getClient();


        String collectionId = "MyCollection";
        System.out.println("Creating collection: " +
                collectionId );

        CreateCollectionRequest request = new CreateCollectionRequest()
                .withCollectionId(collectionId);

        CreateCollectionResult createCollectionResult = amazonRekognition.createCollection(request);
        System.out.println("CollectionArn : " +
                createCollectionResult.getCollectionArn());
        System.out.println("Status code : " +
                createCollectionResult.getStatusCode().toString());

    }
}
