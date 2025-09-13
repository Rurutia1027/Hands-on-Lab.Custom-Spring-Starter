package com.aston.handson.autoconfigure;

import com.aston.handson.loader.SpringPropertiesLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3BaseConfiguration {
    @Bean
    public SpringPropertiesLoader springPropertiesLoader() {
        return new SpringPropertiesLoader();
    }
}