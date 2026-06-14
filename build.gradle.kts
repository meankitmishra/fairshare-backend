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
  
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  
  implementation("org.springframework.modulith:spring-modulith-api")
 
  
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.modulith:spring-modulith-starter-test")
  testImplementation("org.springframework.modulith:spring-modulith-docs")
  testImplementation(libs.archunit.junit5)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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