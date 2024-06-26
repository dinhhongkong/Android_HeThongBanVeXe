plugins {
    alias(libs.plugins.androidApplication)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
//        dataBinding = true
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // LIBRARY JAR.
    implementation(
        fileTree(
            mapOf(
                "dir" to "C:\\Users\\KongDinh\\Documents\\Project\\DichVuHeThongDatVeXe\\Android_HeThongBanVeXe\\ZPDK-Android",
                "include" to listOf("*.aar")
            )
        )
    )
//    implementation(
//        fileTree(
//            mapOf(
//                "dir" to "C:\\Users\\HP\\Documents\\CoachTicketBookingApp\\ZPDK-Android",
//                "include" to listOf("*.aar")
//            )
//        )
//    )

    implementation("com.squareup.retrofit2:retrofit: 2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")


    implementation(libs.jwtdecode)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation("androidx.core:core-ktx:+")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}