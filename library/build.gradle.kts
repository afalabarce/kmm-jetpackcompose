import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.com.google.ksp)
    //alias(libs.plugins.native.cocoapods)
    kotlin("native.cocoapods")
    id("module.publication")
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    targetHierarchy.default()

    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = BuildVersion.Android.jvmTarget
            }
        }
    }

    jvm()
    //https://github.com/ashleymills/Reachability.swift
    val xcframeworkName = "ReachabilitySwift"
    val xcf = XCFramework(xcframeworkName)

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        ios.deploymentTarget = "12.0"
        pod("Reachability") {
            version = "3.2"
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlin.experimental.ExperimentalNativeApi")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.android.core.ktx)
                implementation(libs.accompanist.permission.android)
            }
        }

        val commonMain by getting {
            dependencies {
                // Moved from implementation to api due to below issue
                // https://issuetracker.google.com/issues/294869453
                // https://github.com/JetBrains/compose-multiplatform/issues/3927
                api(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.uiToolingPreview)
                //implementation(libs.constraint.layout)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
            }
        }

        val iosMain by getting {
            dependencies {

            }
        }
    }
}

android {
    namespace = BuildVersion.Environment.libraryBasePackage
    compileSdk = BuildVersion.Android.compileSdkVersion
    defaultConfig {
        minSdk = BuildVersion.Android.minSdkVersion
    }

    compileOptions {
        sourceCompatibility = BuildVersion.Android.javaCompatibility
        targetCompatibility = BuildVersion.Android.javaCompatibility
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompilerVersion.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

task("testClasses").doLast {
    println("This is a dummy testClasses task")
}
