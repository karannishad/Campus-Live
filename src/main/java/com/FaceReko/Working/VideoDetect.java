package com.FaceReko.Working;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.sqs.model.Message;
import java.util.List;

public class VideoDetect {

    private static String bucket = "criminal-record-bucket";
    private static String video = "inpu4.mp4";
    private static String queueUrl =  "https://sqs.ap-south-1.amazonaws.com/312872316571/RekognitionQueue";
    private static String topicArn="arn:aws:sns:ap-south-1:312872316571:AmazonRekognitionTopic";
    private static String roleArn="arn:aws:iam::312872316571:role/serviceRekognition";

    private static AmazonSQS sqs = null;
    private static AmazonRekognition rek = null;

    private static NotificationChannel channel= new NotificationChannel()
            .withSNSTopicArn(topicArn)
            .withRoleArn(roleArn);


    private static String startJobId = null;
    private static void StartFaces(String bucket, String video) throws Exception{

        StartFaceDetectionRequest req = new StartFaceDetectionRequest()
                .withVideo(new Video()
                        .withS3Object(new S3Object()
                                .withBucket(bucket)
                                .withName(video)))
                .withNotificationChannel(channel);



        StartFaceDetectionResult startLabelDetectionResult = rek.startFaceDetection(req);
        startJobId=startLabelDetectionResult.getJobId();

    }
    private static void StartFaceSearchCollection(String bucket, String video) throws Exception{


        StartFaceSearchRequest req = new StartFaceSearchRequest()
                .withCollectionId("MyCollection")
                .withVideo(new Video()
                        .withS3Object(new S3Object()
                                .withBucket(bucket)
                                .withName(video))).withFaceMatchThreshold(99F)
                .withNotificationChannel(channel);



        StartFaceSearchResult startPersonCollectionSearchResult = rek.startFaceSearch(req);
        startJobId=startPersonCollectionSearchResult.getJobId();

    }

    //Face collection search in video ==================================================================
    private static void GetResultsFaceSearchCollection() throws Exception{

        GetFaceSearchResult faceSearchResult=null;
        int maxResults=10;
        String paginationToken=null;

        do {

            if (faceSearchResult !=null){
                paginationToken = faceSearchResult.getNextToken();
            }


            faceSearchResult  = rek.getFaceSearch(
                    new GetFaceSearchRequest()
                            .withJobId(startJobId)
                            .withMaxResults(maxResults)
                            .withNextToken(paginationToken)
                            .withSortBy(FaceSearchSortBy.TIMESTAMP)
            );


            VideoMetadata videoMetaData=faceSearchResult.getVideoMetadata();

            System.out.println("Format: " + videoMetaData.getFormat());
            System.out.println("Codec: " + videoMetaData.getCodec());
            System.out.println("Duration: " + videoMetaData.getDurationMillis());
            System.out.println("FrameRate: " + videoMetaData.getFrameRate());
            System.out.println();


            //Show search results
            List<PersonMatch> matches=
                    faceSearchResult.getPersons();

            for (PersonMatch match: matches) {
                long milliSeconds=match.getTimestamp();
                System.out.print("Timestamp: " + Long.toString(milliSeconds));
                System.out.println(" Person number: " + match.getPerson().getIndex());
                List <FaceMatch> faceMatches = match.getFaceMatches();
                if (faceMatches != null) {
                    System.out.println("Matches in collection...");
                    for (FaceMatch faceMatch: faceMatches){
                        Face face=faceMatch.getFace();
                        System.out.println("Face Id: "+ face.getFaceId());
                        System.out.println("Similarity: " + faceMatch.getSimilarity().toString());
                        System.out.println();
                    }
                }
                System.out.println();
            }

            System.out.println();

        } while (faceSearchResult !=null && faceSearchResult.getNextToken() != null);

    }
    private static void GetResultsFaces() throws Exception{

        int maxResults=10;
        String paginationToken=null;
        GetFaceDetectionResult faceDetectionResult=null;

        do{
            if (faceDetectionResult !=null){
                paginationToken = faceDetectionResult.getNextToken();
            }

            faceDetectionResult = rek.getFaceDetection(new GetFaceDetectionRequest()
                    .withJobId(startJobId)
                    .withNextToken(paginationToken)
                    .withMaxResults(maxResults));

            VideoMetadata videoMetaData=faceDetectionResult.getVideoMetadata();

            System.out.println("Format: " + videoMetaData.getFormat());
            System.out.println("Codec: " + videoMetaData.getCodec());
            System.out.println("Duration: " + videoMetaData.getDurationMillis());
            System.out.println("FrameRate: " + videoMetaData.getFrameRate());


            //Show faces, confidence and detection times
            List<FaceDetection> faces= faceDetectionResult.getFaces();

            for (FaceDetection face: faces) {
                long seconds=face.getTimestamp()/1000;
                System.out.print("Sec: " + Long.toString(seconds) + " ");
                System.out.println(face.getFace().toString());
                System.out.println();
            }
        } while (faceDetectionResult !=null && faceDetectionResult.getNextToken() != null);


    }



    public static void main(String[] args)  throws Exception{
        AWSCredentials credentials= new ProfileCredentialsProvider().getCredentials();


        sqs = AmazonSQSClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        rek = AmazonRekognitionClientBuilder.standard()
                .withRegion(Regions.AP_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

        //=================================================
        StartFaceSearchCollection(bucket, video);
        //=================================================
        System.out.println("Waiting for job: " + startJobId);
        //Poll queue for messages
        List<Message> messages=null;
        int dotLine=0;
        boolean jobFound=false;

        //loop until the job status is published. Ignore other messages in queue.
        do{
            messages = sqs.receiveMessage(queueUrl).getMessages();
            if (dotLine++<20){
                System.out.print(".");
            }else{
                System.out.println();
                dotLine=0;
            }

            if (!messages.isEmpty()) {
                //Loop through messages received.
                for (Message message: messages) {
                    String notification = message.getBody();

                    // Get status and job id from notification.
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode jsonMessageTree = mapper.readTree(notification);
                    JsonNode messageBodyText = jsonMessageTree.get("Message");
                    ObjectMapper operationResultMapper = new ObjectMapper();
                    JsonNode jsonResultTree = operationResultMapper.readTree(messageBodyText.textValue());
                    JsonNode operationJobId = jsonResultTree.get("JobId");
                    JsonNode operationStatus = jsonResultTree.get("Status");
                    System.out.println("Job found was " + operationJobId);
                    // Found job. Get the results and display.
                    if(operationJobId.asText().equals(startJobId)){
                        jobFound=true;
                        System.out.println("Job id: " + operationJobId );
                        System.out.println("Status : " + operationStatus.toString());
                        if (operationStatus.asText().equals("SUCCEEDED")){
                            //============================================
                            GetResultsFaceSearchCollection();
                            //============================================
                        }
                        else{
                            System.out.println("Video analysis failed");
                        }

                        sqs.deleteMessage(queueUrl,message.getReceiptHandle());
                    }

                    else{
                        System.out.println("Job received was not job " +  startJobId);
                        //Delete unknown message. Consider moving message to dead letter queue
                        sqs.deleteMessage(queueUrl,message.getReceiptHandle());
                    }
                }
            }
        } while (!jobFound);


        System.out.println("Done!");
    }


    private static void StartLabels(String bucket, String video) throws Exception{

        StartLabelDetectionRequest req = new StartLabelDetectionRequest()
                .withVideo(new Video()
                        .withS3Object(new S3Object()
                                .withBucket(bucket)
                                .withName(video)))
                .withMinConfidence(50F)
                .withJobTag("DetectingLabels")
                .withNotificationChannel(channel);

        StartLabelDetectionResult startLabelDetectionResult = rek.startLabelDetection(req);
        startJobId=startLabelDetectionResult.getJobId();


    }



    private static void GetResultsLabels() throws Exception{

        int maxResults=25;
        String paginationToken=null;
        GetLabelDetectionResult labelDetectionResult=null;

        do {
            if (labelDetectionResult !=null){
                paginationToken = labelDetectionResult.getNextToken();
            }

            GetLabelDetectionRequest labelDetectionRequest= new GetLabelDetectionRequest()
                    .withJobId(startJobId)
                    .withSortBy(LabelDetectionSortBy.TIMESTAMP)
                    .withMaxResults(maxResults)
                    .withNextToken(paginationToken);


            labelDetectionResult = rek.getLabelDetection(labelDetectionRequest);

            VideoMetadata videoMetaData=labelDetectionResult.getVideoMetadata();

            System.out.println("Format: " + videoMetaData.getFormat());
            System.out.println("Codec: " + videoMetaData.getCodec());
            System.out.println("Duration: " + videoMetaData.getDurationMillis());
            System.out.println("FrameRate: " + videoMetaData.getFrameRate());


            //Show labels, confidence and detection times
            List<LabelDetection> detectedLabels= labelDetectionResult.getLabels();

            for (LabelDetection detectedLabel: detectedLabels) {
                long seconds=detectedLabel.getTimestamp();
                Label label=detectedLabel.getLabel();
                System.out.println("Millisecond: " + Long.toString(seconds) + " ");

                System.out.println("   Label:" + label.getName());
                System.out.println("   Confidence:" + detectedLabel.getLabel().getConfidence().toString());

                List<Instance> instances = label.getInstances();
                System.out.println("   Instances of " + label.getName());
                if (instances.isEmpty()) {
                    System.out.println("        " + "None");
                } else {
                    for (Instance instance : instances) {
                        System.out.println("        Confidence: " + instance.getConfidence().toString());
                        System.out.println("        Bounding box: " + instance.getBoundingBox().toString());
                    }
                }
                System.out.println("   Parent labels for " + label.getName() + ":");
                List<Parent> parents = label.getParents();
                if (parents.isEmpty()) {
                    System.out.println("        None");
                } else {
                    for (Parent parent : parents) {
                        System.out.println("        " + parent.getName());
                    }
                }
                System.out.println();
            }
        } while (labelDetectionResult !=null && labelDetectionResult.getNextToken() != null);

    }
}
