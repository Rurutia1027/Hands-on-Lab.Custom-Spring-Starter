package com.aston.handson.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3ClientService {
    private final S3Client s3Client;

    public boolean isS3ClientAvailable() {
        return Objects.nonNull(s3Client) && Objects.nonNull(s3Client.listBuckets());
    }

    /**
     * Upload file from byte array
     */
    public void upload(String bucket, String key, byte[] content) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                RequestBody.fromBytes(content)
        );
    }

    /**
     * Download file as byte array
     */
    public byte[] download(String bucket, String key) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        return s3Client.getObjectAsBytes(getRequest).asByteArray();
    }

    /**
     * Delete object
     */
    public void delete(String bucket, String key) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        s3Client.deleteObject(deleteRequest);
    }

    /**
     * List objects in bucket
     */
    public List<String> listObjects(String bucket) {
        ListObjectsRequest listRequest = ListObjectsRequest.builder()
                .bucket(bucket)
                .build();

        return s3Client.listObjects(listRequest)
                .contents()
                .stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }
}