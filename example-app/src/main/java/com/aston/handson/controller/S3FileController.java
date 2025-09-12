package com.aston.handson.controller;

import com.aston.handson.core.S3ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (s3ClientService != null) {
            return "S3ClientService is available!";
        } else {
            return "S3ClientService is null!";
        }
    }
}
