package com.aston.handson.core.config;

import lombok.Getter;
import lombok.Setter;

public class ApplicationProperties {
    /**
     * App name
     */
    @Getter
    @Setter
    private static String applicationName;

    /**
     * Spring App active profile
     */
    @Getter
    @Setter
    private static String activeProfile;
}
