plugins {
    kotlin("jvm") version "2.3.10"
    kotlin("plugin.serialization") version "2.3.10"
    id("com.gradleup.shadow") version "9.3.1"
}

group = "io.wesner.robert.cb1060.clamprotect.core"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://maven.robert.wesner.io/repository/maven-public/")
    maven("https://maven.robert.wesner.io/repository/johnymuffin-maven-public/")
}

dependencies {
    implementation("com.legacyminecraft.poseidon:poseidon-craftbukkit:1.+")
    implementation("com.rabbitmq:amqp-client:5.29.0")
    implementation("com.akuleshov7:ktoml-core:0.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
}

kotlin {
    jvmToolchain(8)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(project.properties)
    }
}

tasks.shadowJar {
    archiveClassifier = ""
}
