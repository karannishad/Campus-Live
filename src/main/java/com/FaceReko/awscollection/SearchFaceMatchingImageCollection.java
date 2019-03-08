package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.FaceMatch;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.SearchFacesByImageRequest;
import com.amazonaws.services.rekognition.model.SearchFacesByImageResult;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class SearchFaceMatchingImageCollection {
    public static final String collectionId = "MyCollection";
    public static final String photo = "/home/consultadd/Downloads/index.jpeg";

    public static void main(String[] args) throws Exception {

        AmazonRekognition rekognitionClient = Credentials.getClient();

        ObjectMapper objectMapper = new ObjectMapper();

        // Get an image object from local
        ByteBuffer imagebyte;
        try(InputStream inputStream =new FileInputStream(new File(photo)) ){
            imagebyte =ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        Image image=new Image()
                .withBytes(imagebyte);

        // Search collection for faces similar to the largest face in the image.
        SearchFacesByImageRequest searchFacesByImageRequest = new SearchFacesByImageRequest()
                .withCollectionId(collectionId)
                .withImage(image)
                .withFaceMatchThreshold(70F)
                .withMaxFaces(10);

        SearchFacesByImageResult searchFacesByImageResult =
                rekognitionClient.searchFacesByImage(searchFacesByImageRequest);

        System.out.println("Faces matching largest face in image from" + photo);
        List<FaceMatch> faceImageMatches = searchFacesByImageResult.getFaceMatches();
        for (FaceMatch face: faceImageMatches) {
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(face));
            System.out.println();
        }
    }
}
