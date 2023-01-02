plugins {
    kotlin("multiplatform")
    id("org.jetbrains.dokka")
    `maven-publish`
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
            }
        }
        val nativeTest by getting
    }

    // native main for testing
    nativeTarget.apply {
        compilations["main"].cinterops {
            val gtk by creating
        }

        binaries {
            all {
                if (isMingwX64) {
                    val userHome = File(System.getenv("USERPROFILE"))
                    linkerOpts(
                        "-L${userHome}\\.konan\\dependencies\\msys2-mingw-w64-x86_64-2\\x86_64-w64-mingw32\\lib",
                        "-LC:\\msys64\\mingw64\\lib",
                    )
                }
            }
        }
    }
}