plugins {
    id("com.android.application")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.firebase-perf")     // Add the Performance Monitoring Gradle plugin
    id("com.google.firebase.crashlytics")       // Add the Crashlytics Gradle plugin
}

android {
    namespace = "com.example.madapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.madapp"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

// dependencies for Maps API
buildscript {
    dependencies {
        classpath ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    implementation("androidx.browser:browser:1.7.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-crashlytics-buildtools:2.9.9")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.json:json:20220320")
    implementation("com.github.KwabenBerko:News-API-Java:1.0.2")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.0")
    implementation ("androidx.annotation:annotation:1.3.0")
    implementation ("org.apache.httpcomponents:httpclient-android:4.3.5.1")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("org.jsoup:jsoup:1.14.3")
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))     // BoM for Firebase platform
    implementation("com.google.firebase:firebase-perf")     // Do not need to specify the ver of performance tool
    implementation("com.google.firebase:firebase-analytics")        // dependency for firebase analytics
    implementation("com.google.firebase:firebase-crashlytics")      // dependency for crash analytics
}
