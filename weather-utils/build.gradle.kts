plugins {
    id("java-library")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

group = "org.example"
version = "1.0-SNAPSHOT"