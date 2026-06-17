package com.fairshare.backend.receipts.internal;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfiguration {
  @Bean
  @Lazy
  S3Client s3Client(StorageProperties storageProperties) {
    return S3Client.builder()
        .endpointOverride(URI.create(storageProperties.endpoint()))
        .region(Region.of(storageProperties.region()))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(
                    storageProperties.accessKey(), storageProperties.secretkey())))
        .forcePathStyle(true)
        .build();
  }
}
