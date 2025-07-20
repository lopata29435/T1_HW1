import org.gradle.kotlin.dsl.invoke

plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":weather-utils"))
    implementation("org.apache.kafka:kafka-clients:3.9.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.19.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.19.1")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.consumerservice.WeatherConsumer"
    }
}


