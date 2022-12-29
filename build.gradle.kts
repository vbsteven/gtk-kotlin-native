plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka") version "1.7.20"
}

allprojects {
    group = "io.quantus.gtk-kotlin-native"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

kotlin {
    mingwX64()
    macosX64()
    linuxX64()
}