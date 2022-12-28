plugins {
    kotlin("multiplatform")
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
