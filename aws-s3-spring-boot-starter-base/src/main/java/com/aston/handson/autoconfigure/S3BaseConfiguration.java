package com.aston.handson.autoconfigure;

import com.aston.handson.core.S3ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3BaseConfiguration {
    @Bean
    public S3ClientService s3ClientService() {
        return new S3ClientService(null);
    }
}