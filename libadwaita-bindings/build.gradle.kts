plugins {
    kotlin("multiplatform")
    `maven-publish`
}

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val nativeMain by getting {
            dependencies {
                api(project(":gobject-bindings"))
                api(project(":gio-bindings"))
                api(project(":gtk-bindings"))
            }
        }
        val nativeTest by getting
    }

    nativeTarget.apply {
        compilations["main"].cinterops {
            val adwaita by creating
        }
    }
}