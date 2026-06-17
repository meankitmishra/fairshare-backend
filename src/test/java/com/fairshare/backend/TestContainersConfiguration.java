package com.fairshare.backend;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestContainersConfiguration {
  @Bean
  @ServiceConnection
  PostgreSQLContainer postgreContainer() {
    return new PostgreSQLContainer("postgres:17");
  }

  @Bean
  @ServiceConnection(name = "redis")
  GenericContainer<?> redisContainer() {
    return new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);
  }
}
