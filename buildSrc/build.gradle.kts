plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.1")
    implementation("org.jacoco:org.jacoco.core:0.8.5")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:1.6.2.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:2.8")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.28.1-alpha")

    implementation(gradleApi())
    implementation(localGroovy())
}