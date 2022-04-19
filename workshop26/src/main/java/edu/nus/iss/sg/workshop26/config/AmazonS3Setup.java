package edu.nus.iss.sg.workshop26.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Setup {

    @Value("${spaces.endpoint}")
    private String endpoint;

    @Value("${spaces.region}")
    private String region;

    @Value("${spaces.access.key}")
    private String accessKey;

    @Value("${spaces.secret.key}")
    private String secretKey;

    @Bean
    public AmazonS3 create() {
        AWSStaticCredentialsProvider cred = new AWSStaticCredentialsProvider(
                                                new BasicAWSCredentials(accessKey, secretKey));

        EndpointConfiguration config  = new EndpointConfiguration(endpoint, region);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(cred)
                .withEndpointConfiguration(config)
                .build();
    }
    
}
