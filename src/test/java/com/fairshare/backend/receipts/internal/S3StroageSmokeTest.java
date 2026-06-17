package com.fairshare.backend.receipts.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MinIOContainer;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

class S3StroageSmokeTest {
    static MinIOContainer minio;
    static S3Client s3;

    @BeforeAll
    static void startContainer() {
        minio = new MinIOContainer("minio/minio:latest");
        minio.start();
        s3 = S3Client.builder()
                .endpointOverride(URI.create(minio.getS3URL()))
                .region(Region.AP_SOUTH_2)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(minio.getUserName(), minio.getPassword())))
                .forcePathStyle(true)
                .build();
    }

    @AfterAll
    static void stopContainer() {
        if (minio != null) {
            minio.stop();
        }
    }

    @Test
    void putAndGetRoundTrips() {
        String bucket = "test-bucket";
        String key = "hello.txt";
        byte[] payload = "hello fairshare".getBytes();

        s3.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
        s3.putObject(
                PutObjectRequest.builder().bucket(bucket).key(key).build(), RequestBody.fromBytes(payload));

        ResponseBytes<?> result = s3.getObjectAsBytes(GetObjectRequest.builder().bucket(bucket).key(key).build());
        assertThat(result.asByteArray()).isEqualTo(payload);
    }
}
