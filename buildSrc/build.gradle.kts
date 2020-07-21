plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    jcenter()
    google()
    maven {
        url = uri("https://jitpack.io")
    }
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:1.6.2.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.0")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.28.1-alpha")
    implementation("org.jacoco:org.jacoco.core:0.8.5")
    implementation("com.hiya:jacoco-android:0.2")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.0")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")

    implementation(gradleApi())
    implementation(localGroovy())
}