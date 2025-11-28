plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    //Hilt
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.application.jetweatherforecast"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.application.jetweatherforecast"
        minSdk = 30
        targetSdk = 36
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

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.57.2")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.3.0")
    //Dagger - Hilt
    ksp("com.google.dagger:hilt-android-compiler:2.57.2")
    ksp("androidx.hilt:hilt-compiler:1.3.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
    // Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2")
    //lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    //coil
    implementation("io.coil-kt:coil-compose:2.7.0")
    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //Json Converter
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    //Room
    implementation("androidx.room:room-runtime:2.8.4")
    //KSP
    ksp("androidx.room:room-compiler:2.8.4")
    //annotationProcessor
//    annotationProcessor("androidx.room:room-compiler:2.8.4")
    //Coroutines
    implementation("androidx.room:room-ktx:2.8.4")
    //Icons
    implementation("androidx.compose.material:material-icons-extended")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}