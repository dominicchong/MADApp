buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply true    // id for Maps API
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false     // Performance Monitoring Gradle plugin
    id("com.google.firebase.crashlytics") version "2.9.9" apply false       // Crashlytics Gradle plugin
}