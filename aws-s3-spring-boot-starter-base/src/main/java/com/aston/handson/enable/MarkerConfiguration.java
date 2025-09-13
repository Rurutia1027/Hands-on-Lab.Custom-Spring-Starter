package com.aston.handson.enable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkerConfiguration {

    @Bean
    public Marker dynamicS3ClientServiceMarkerBean() {
        return new Marker();
    }


    public class Marker {

    }
}
