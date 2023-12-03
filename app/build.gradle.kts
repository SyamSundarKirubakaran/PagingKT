plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "work.syam.pagingkt"
    compileSdk = 34

    defaultConfig {
        applicationId = "work.syam.pagingkt"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"47ea50d7ece7e74f25725d5937da4586\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit library dependency + GSON
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson support adapter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Retrofit support adapter for RxJava
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    // Optional - Okhttp logging library for debug purpose
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("androidx.paging:paging-runtime-ktx:3.0.1")
    // RxJava3 support for paging library
    implementation("androidx.paging:paging-rxjava3:3.0.1")

    // Image loading library support
    implementation("com.squareup.picasso:picasso:2.71828")
}