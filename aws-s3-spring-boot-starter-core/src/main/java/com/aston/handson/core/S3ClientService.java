package com.aston.handson.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class S3ClientService {
    private final S3Client s3Client;

    public void upload(String bucket, String key, String filePath) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                Paths.get(filePath)
        );
    }
}