package com.aston.handson.autoconfigure;

import com.aston.handson.core.service.S3ClientService;
import com.aston.handson.enable.MarkerConfiguration;
import com.aston.handson.properties.S3Properties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@ConditionalOnBean(MarkerConfiguration.Marker.class)
@Import(S3BaseConfiguration.class)
@AutoConfigureAfter(S3BaseConfiguration.class)
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
        return s3Client;
    }

    @Bean
    @ConditionalOnMissingBean
    public S3ClientService s3ClientService(S3Client s3Client) {
        return new S3ClientService(s3Client);
    }
}