buildscript {
    repositories {
        google()
        jcenter()
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}