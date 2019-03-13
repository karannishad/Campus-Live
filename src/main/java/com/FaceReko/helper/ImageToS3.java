package com.FaceReko.helper;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

public class ImageToS3 {


    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    String bucketName="imagefacereko";
   static AmazonS3 amazonS3=Credentials.getS3Client();

    private static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
//
//    private void uploadFileTos3bucket(String fileName,byte[] byte1){
//
//        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, Files.write("gad",byte1)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//    }

//    public String uploadFile(MultipartFile multipartFile) {
//
//        String fileUrl = "";
//        try {
//            File file = convertMultiPartToFile(multipartFile);
//            String fileName = generateFileName(multipartFile);
//            fileUrl =endpointUrl+ "/" + bucketName + "/" + fileName;
//            uploadFileTos3bucket(fileName, file);
//            file.delete();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileUrl;
//    }

//    public static void main(String[] args) {
//        amazonS3.putObject();
//    }
}
