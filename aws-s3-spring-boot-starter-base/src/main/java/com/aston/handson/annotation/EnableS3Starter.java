package com.aston.handson.annotation;

import com.aston.handson.autoconfigure.S3BaseConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(S3BaseConfiguration.class)
public @interface EnableS3Starter {
}
