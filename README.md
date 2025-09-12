# Hands-on Lab: Custom Spring Starter for AWS S3 

This is a **hands-on example** of building a custom Spring Starter for AWS S3. The starter encapsulates **AWS SDK configuration** and exposes a simple S3Service bean, so your Spring Boot applications can use S3 with minimal setup. 

The project demostrates:
- How to create a **custom Spring Starter**
- How to use **annotation-driven feature toggle(`@EnableS3Starter`)**
- How auto-configuration simplifies integration with external services
- How Spring **lifecycle hooks** interact with starter beans 

## Features 
- Automatic creation of an `S3Client` using Spring Boot configuration properties. 
- Easy-to-use `S3Service` for uploading, downloading, and listing objects.
- Spring Boot-friendly: fully **annotation-driven** and configurable via `application.properties`, `application.yml` or `application.yaml`.

## Project Structure 

```
hands-on-lab-aws-s3-starter/
├── aws-s3-starter-core/           # Core logic: S3Service, properties, clients
├── aws-s3-starter-spring-base/    # Annotation and base configuration
├── aws-s3-starter/                # Spring Starter module with auto-configuration
└── example-app/                   # Example application showing hands-on usage
```

## How Spring lifecycle and Starter Work Together 
When you use the starter in your Spring Boot app:
### Annotation Detection 
- `@EnableS3Starter` is meta-annotated with `@Import(S3MarkerConfiguration.class)`
- Spring scans the application class, detects the annotation, and registers the marker bean


### Auto-Configuration Triggered
- `S3AutoConfiguration` is conditional: `@ConditionalOnBean(S3Marker.class)` and `@ConditionOnProperty("s3.starter.enabled")`
- Spring evaluates these conditions after the context has scanned annotations but **before beans are fully initialized**

### Bean Instantiation 
- `S3Client` and `S3Service` beans are created automatically by Spring
- Properties are injected from `application.properties` or `application.yml`


### Application Context Ready 
- All beans are now part of the Spring ApplicationContext
- Your application can `@Autowired S3Service` and immediately start using it

> Essentially: **Annotation** -> **Marker** -> **Auto-Configuration** -> **Bean** -> **Ready-to-use Service** 


## Getting Started 
Follow these steps to use the **AWS S3 Spring Boot Starter** in your own project.

### Build the Starter 
Clone the repository and install the starter into your local Maven repository:
```bash
git clone https://github.com/Rurutia1027/Hands-on-Lab.Custom-Spring-Starter.git
cd Hands-on-Lab.Custom-Spring-Starter
./mvnw clean install
```

### Run the Provided Example App (Optional)
You can quickly test the starter by running the included example: 
```bash 
cd example-app
./mvnw spring-boot:run
```

The example app already includes configuration and demostrates how to upload objects to S3. 

### Add Dependency in Your Own Project 
In your own Spring Boot application, add the starter dependency:
```xml
<dependency>
  <groupId>com.aaston.handson</groupId>
  <artifactId>aws-s3-spring-boot-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Enable the Starter 
Add the annotation to your application main class:
```java
@SpringBootApplication
@EnableS3Starter
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### Configure AWS Credentials 
Add the following configuration to your `application.yml`

```yaml
s3:
  enable: true
aws:
  access-key: your-aws-access-key
  secret-key: your-aws-secret-key
  region: us-east-1
```

You can also use `environment variables` if you prefer: 

```bash 
export AWS_ACCESS_KEY_ID=your-aws-access-key
export AWS_SECRET_ACCESS_KEY=your-aws-secret-key
```

### Use in Your Application 
Now you can inject the S3 service directly: 

```java
@RestController
@RequiredArgsConstructor
public class FileController {

    // this instance will be injected to your spring context via auto-config 
    private final S3ClientService s3ClientService;

    @PostMapping("/upload")
    public String upload(@RequestParam String bucket, 
                         @RequestParam String key, 
                         @RequestParam String content) {
        s3ClientService.upload(bucket, key, content);
        return "Uploaded successfully!";
    }
}
```

### Done!
Your application now has AWS S3 integration via the custom Spring Boot Starter -- no boilerplate required. 


## About Spring Starters 
Spring Starter is a **packaging convention** and **auto-configuration mechanism** in the Spring ecosystem:
- It bundles **common dependencies**, **configuration**, and **beans** into a reusable module. 
- You can **import the starter via Maven/Gradle**, and Spring Boot automatically picks up its auto-configuration classes. 
- Starters often use **a marker class** + **annotation** pattern to allow **annotation-driven activation**, meaning that adding `@EnableXxx` or setting a properly is enough to bring the feature alive. 
- They reduce **boilerplate** and **setup complexity**, allowing teams to focus on business logic instead of wiring up clients, services, or SDKs manually. 

### In our S3 example:
- **Core module** contains the actual **AWS SDK** client and service logic.
- **Spring Base module** provides the `@EnableS3Starter` annotation and marker bean. 
- **Starter module** ties it all together with `S3AutoConfiguration`, which registers the beans **conditionally** based on the presence of the marker and configuration properties. 

__This pattern ensures that your application **only loads the S3 integration when explicitly enabled**, keeping the context clean and minimizing unused resources.__