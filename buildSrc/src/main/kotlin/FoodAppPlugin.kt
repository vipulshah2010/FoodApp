import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class FoodAppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (project.hasProperty("android")) {
            project.configureAndroidBlock()
            project.configureCommonDependencies()
        }
    }
}

internal fun Project.configureAndroidBlock() = extensions.getByType<BaseExtension>().run {

    buildToolsVersion("29.0.3")
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

internal fun Project.configureCommonDependencies() {

    extensions.getByType<BaseExtension>().run {
        dependencies {
            add("implementation", Deps.coroutine)
            add("implementation", Deps.coroutineCore)
            add("implementation", Deps.coroutineTest)
            add("implementation", Deps.kotlin)

            add("implementation", Deps.core)
            add("implementation", Deps.coreKtx)
            add("implementation", Deps.fragment)
            add("implementation", Deps.liveData)
            add("implementation", Deps.lifecycleCommon)
            add("kapt", Deps.lifecycleCompiler)
            add("implementation", Deps.lifecycleExt)
            add("implementation", Deps.lifecycleViewModel)
            add("implementation", Deps.navigationFragment)
            add("implementation", Deps.navigationUI)

            add("implementation", Deps.cardView)
            add("implementation", Deps.circleImage)
            add("implementation", Deps.constraint)
            add("implementation", Deps.material)
            add("implementation", Deps.skeletonLoading)

            add("implementation", Deps.commonsLang)
            add("implementation", Deps.gson)
            add("implementation", Deps.hilt)
            add("kapt", Deps.hiltCompiler)
            add("implementation", Deps.hiltViewModel)
            add("kapt", Deps.hiltViewModelCompiler)
            add("implementation", Deps.room)
            add("implementation", Deps.roomKtx)
            add("kapt", Deps.roomCompiler)
            add("implementation", Deps.retrofit)
            add("implementation", Deps.converter)
            add("implementation", Deps.retrofitInterceptor)
            add("implementation", Deps.picasso)

            add("implementation", Deps.fragmentTesting)
            add("testImplementation", Deps.archCoreTesting)
            add("testImplementation", Deps.roomTesting)
            add("implementation", Deps.testCoreDep)
            add("androidTestImplementation", Deps.espresso)
            add("androidTestImplementation", Deps.espressoContrib)
            add("androidTestImplementation", Deps.espressoIdling)
            add("androidTestImplementation", Deps.espressoIntents)
            add("testImplementation", Deps.jU5api)
            add("testRuntimeOnly", Deps.jU5Engine)
            add("androidTestImplementation", Deps.jUnitExt)
            add("testImplementation", Deps.mockk)
            add("androidTestUtil", Deps.orchestrator)
            add("androidTestImplementation", Deps.rules)
            add("androidTestImplementation", Deps.runner)
            add("androidTestImplementation", Deps.uiAutomator)
        }
    }
}