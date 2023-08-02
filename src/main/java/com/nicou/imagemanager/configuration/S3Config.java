package com.nicou.imagemanager.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class S3Config {

    @Value("${s3.bucket}")
    private String s3Bucket;
    @Value("${s3.access}")
    private String s3Access;
    @Value("${s3.secret}")
    private String s3secret;
    @Value("${s3.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        final AWSCredentials credentials = new BasicAWSCredentials(s3Access, s3secret);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}
