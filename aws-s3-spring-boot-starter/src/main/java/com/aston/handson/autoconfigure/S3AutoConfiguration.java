package com.aston.handson.autoconfigure;

import com.aston.handson.core.S3ClientService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableConfigurationProperties(S3Properties.class)
@ConditionalOnProperty(prefix = "s3", name = "enable", havingValue = "true", matchIfMissing = true)
public class S3AutoConfiguration {
    @Bean
    public S3Client s3Client(S3Properties properties) {
        S3Client s3Client = S3Client.builder()
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())
                        )
                )
                .build();
        // System.out.println(s3Client.listBuckets());
        return s3Client;
    }

    @Bean
    @ConditionalOnMissingBean
    public S3ClientService s3ClientService(S3Client s3Client) {
        return new S3ClientService(s3Client);
    }
}