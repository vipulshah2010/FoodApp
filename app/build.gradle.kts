plugins {
    id("com.android.application")
    id("com.foodapp.plugin")
}

android {

    defaultConfig {
        applicationId = "foodapp.com.foodapp"
        versionCode = 1
        versionName = "1.0"
    }

    sourceSets {
        getByName("androidTest").java.srcDirs("src/sharedTest/java")
        getByName("test").java.srcDirs("src/sharedTest/java")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures.viewBinding = true
}

dependencies {
    implementation("com.android.support:support-core-utils:28.0.0")
    implementation("com.android.support:palette-v7:28.0.0")
    api(project(":domain"))
}
