package com.aston.handson.controller;

import com.aston.handson.core.service.S3ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3FileController {
    private final S3ClientService s3ClientService;

    /**
     * Test if S3ClientService is properly injected
     */
    @GetMapping("/health")
    public String healthCheck() {
        if (s3ClientService != null && s3ClientService.isS3ClientAvailable()) {
            return "S3ClientService is available!";
        } else {
            return "S3ClientService is null!";
        }
    }

    /**
     * Upload file to a specific bucket
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("bucket") String bucket,
                         @RequestParam("file") MultipartFile file) throws IOException {
        String key = file.getOriginalFilename();
        s3ClientService.upload(bucket, key, file.getBytes());
        return "Uploaded: " + key + " to bucket: " + bucket;
    }

    /**
     * Download file from a specific bucket
     */
    @GetMapping("/download")
    public byte[] download(@RequestParam("bucket") String bucket,
                           @RequestParam("key") String key) {
        return s3ClientService.download(bucket, key);
    }

    /**
     * Delete file from a specific bucket
     */
    @DeleteMapping("/delete")
    public String delete(@RequestParam("bucket") String bucket,
                         @RequestParam("key") String key) {
        s3ClientService.delete(bucket, key);
        return "Deleted: " + key + " from bucket: " + bucket;
    }

    /**
     * List objects in a specific bucket
     */
    @GetMapping("/list")
    public List<String> list(@RequestParam("bucket") String bucket) {
        return s3ClientService.listObjects(bucket);
    }

}
