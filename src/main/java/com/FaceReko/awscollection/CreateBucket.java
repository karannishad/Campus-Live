package com.FaceReko.awscollection;

import com.FaceReko.credentials.Credentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class CreateBucket {

    public static Bucket getBucket(String bucket_name) {
        final AmazonS3 s3 =  Credentials.getS3Client();
        Bucket named_bucket = null;
        List<Bucket> buckets = s3.listBuckets();
        for (Bucket b : buckets) {
            if (b.getName().equals(bucket_name)) {
                named_bucket = b;
            }
        }
        return named_bucket;
    }

    public static boolean createBucket(String bucket_name){
        final AmazonS3 s3 =  Credentials.getS3Client();
        Bucket b = null;
        if (s3.doesBucketExist(bucket_name)) {
             return false;
        } else {
            try {
                b = s3.createBucket(bucket_name);
                return true;
            } catch (AmazonS3Exception e) {
                System.err.println(e.getErrorMessage());
                return false;
            }
        }
    }
}
