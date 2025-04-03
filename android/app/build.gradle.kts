import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ssafy.achu"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ssafy.achu"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.messaging.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Jetpack Navigation
    implementation(libs.androidx.navigation.compose)

    //pager
    implementation(libs.accompanist.pager)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)


    //date picker
    implementation(libs.material)

    // Retrofit
    // https://github.com/square/retrofit
    implementation(libs.retrofit)
    // https://github.com/square/okhttp
    implementation(libs.okhttp)
    // https://github.com/square/retrofit/tree/master/retrofit-converters/gson
    implementation(libs.converter.gson)
    // https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
    implementation(libs.logging.interceptor)

    // 이미지 처리
    implementation (libs.androidx.activity.compose.v160)

    // kotlinx-serialization
    implementation(libs.kotlinx.serialization.json)


    //fcm
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.google.firebase.messaging.ktx)


    //lottie
    implementation ("com.airbnb.android:lottie-compose:6.1.0")

}