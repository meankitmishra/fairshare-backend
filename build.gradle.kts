plugins {
  java
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.spotless)
}

group = "com.fairshare"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
  }
}

repositories {
  mavenCentral()
}
 

dependencyManagement {
  imports {
    mavenBom("org.springframework.modulith:spring-modulith-bom:${libs.versions.spring.modulith.get()}")
  }
}
 
dependencies {
  
  testImplementation(platform(libs.testcontainers.bom))
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa") 
  implementation("org.springframework.modulith:spring-modulith-api")
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("org.springframework.boot:spring-boot-starter-flyway")
  implementation(platform(libs.awssdk.bom))
  implementation("software.amazon.awssdk:s3")
  implementation("io.micrometer:micrometer-registry-prometheus")
  implementation("io.micrometer:micrometer-tracing-bridge-otel")
  implementation("io.opentelemetry:opentelemetry-exporter-otlp")

  runtimeOnly("org.flywaydb:flyway-database-postgresql")
  runtimeOnly("org.postgresql:postgresql")
  
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation(libs.testcontainers.junit.jupiter)
  testImplementation(libs.testcontainers.postgresql)
  testImplementation("org.springframework.modulith:spring-modulith-starter-test")
  testImplementation("org.springframework.modulith:spring-modulith-docs")
  testImplementation(libs.archunit.junit5)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation(libs.testcontainers.minio)
  
}
 
tasks.withType<Test>().configureEach {
  useJUnitPlatform()
}
 

spotless {
  java {
    target("src/**/*.java")
    googleJavaFormat()
    removeUnusedImports()
    trimTrailingWhitespace()
    endWithNewline()
  }
}