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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    // Accompanist Pager (ViewPager 같은 기능)
    implementation ("com.google.accompanist:accompanist-pager:0.34.0")

// Pager Indicators (페이지 인디케이터 점)
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.34.0")

// Snapper (스냅 효과)
    implementation ("dev.chrisbanes.snapper:snapper:0.3.0")

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
    implementation (libs.lottie.compose)

    //status bar
    implementation (libs.accompanist.systemuicontroller)
    implementation (libs.accompanist.systemuicontroller.v0301)



    // Krossbow - 코틀린 코루틴 기반 STOMP 클라이언트
    implementation(libs.krossbow.stomp.core)
    implementation(libs.krossbow.websocket.okhttp)
    implementation(libs.krossbow.stomp.kxserialization.json)
}