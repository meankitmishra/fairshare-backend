package com.fairshare.backend.receipts.internal;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fairshare.storage")
public record StorageProperties(
    String endpoint, String region, String bucket, String accessKey, String secretkey) {}
