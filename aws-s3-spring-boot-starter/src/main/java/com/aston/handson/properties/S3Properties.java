package com.aston.handson.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws")
public class S3Properties {
    private String accessKey;
    private String secretKey;
    private String region = "us-east-1";
}
