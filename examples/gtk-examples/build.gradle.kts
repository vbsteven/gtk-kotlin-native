plugins {
    kotlin("multiplatform")
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
                implementation(project(":gtk-bindings"))
            }
        }
    }

    nativeTarget.apply {
        // list all packages in the source dir
        val packageDirs =
            sourceSets["nativeMain"].kotlin.srcDirs
                .first()
                .listFiles()
                ?.filter { it.isDirectory }
                ?: emptyList()

        binaries {
            // add a binary for each package, using its main() fun as the entrypoint
            packageDirs.forEach { pkg ->
                executable(pkg.name) {
                    entryPoint = "${pkg.name}.main"
                }
            }
        }
    }
}