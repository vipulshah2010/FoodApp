import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.TestOptions
import com.hiya.plugins.JacocoAndroidUnitTestReportExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.sonarqube.gradle.SonarQubeExtension


class FoodAppPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        if (project.hasProperty("android")) {
            with(project) {
                configurePlugins()
                configureJacoco()
                configureSonar()
                configureAndroidBlock()
                configureCommonDependencies()
            }
        }
    }
}

internal fun Project.configurePlugins() {
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-android-extensions")
    plugins.apply("kotlin-kapt")
    plugins.apply("dagger.hilt.android.plugin")
    plugins.apply("de.mannodermaus.android-junit5")
    plugins.apply("androidx.navigation.safeargs.kotlin")
}

internal fun Project.configureAndroidBlock() = extensions.getByType<BaseExtension>().run {

    buildToolsVersion("29.0.3")
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("runnerBuilder", "de.mannodermaus.junit5.AndroidJUnit5Builder")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType(KotlinCompile::class.java).all {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
            all {
                extensions
                        .getByType(JacocoTaskExtension::class.java)
                        .isIncludeNoLocationClasses = true
            }
        }
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }
    }

    packagingOptions(action = {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/LICENSE.md")
        exclude("META-INF/LICENSE-notice.md")
    })
}

internal fun Project.configureCommonDependencies() {

    extensions.getByType<BaseExtension>().run {
        dependencies {

            add("kapt", Deps.hiltCompiler)
            add("kapt", Deps.lifecycleCompiler)
            add("kapt", Deps.hiltViewModelCompiler)
            add("kapt", Deps.roomCompiler)

            add("implementation", Deps.coroutine)
            add("implementation", Deps.coroutineCore)
            add("implementation", Deps.coroutineTest)
            add("implementation", Deps.kotlin)

            add("implementation", Deps.core)
            add("implementation", Deps.coreKtx)
            add("implementation", Deps.fragment)
            add("implementation", Deps.liveData)
            add("implementation", Deps.lifecycleCommon)

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
            add("implementation", Deps.hiltViewModel)

            add("implementation", Deps.room)
            add("implementation", Deps.roomKtx)

            add("implementation", Deps.retrofit)
            add("implementation", Deps.converter)
            add("implementation", Deps.retrofitInterceptor)
            add("implementation", Deps.coil)

            add("debugImplementation", Deps.fragmentTesting)

            add("implementation", Deps.testCoreDep)

            add("androidTestImplementation", Deps.espresso)
            add("androidTestImplementation", Deps.espressoContrib)
            add("androidTestImplementation", Deps.espressoIdling)
            add("androidTestImplementation", Deps.espressoIntents)
            add("androidTestImplementation", Deps.jU5TestCore)
            add("androidTestImplementation", Deps.jUnitExt)
            add("androidTestImplementation", Deps.rules)
            add("androidTestImplementation", Deps.runner)
            add("androidTestImplementation", Deps.uiAutomator)

            add("testRuntimeOnly", Deps.jU5Engine)
            add("testRuntimeOnly", Deps.jU5Engine)
            add("androidTestRuntimeOnly", Deps.jU5TestRunner)
            add("androidTestUtil", Deps.orchestrator)

            add("testImplementation", Deps.archCoreTesting)
            add("testImplementation", Deps.roomTesting)
            add("testImplementation", Deps.truth)
            add("testImplementation", Deps.mockk)
            add("testImplementation", Deps.jU5api)
            add("testImplementation", Deps.turbine)
        }
    }
}

/**
 * Jacoco is a tool to measure code coverage and currently the most widely used.
 * This method would apply [com.hiya.jacoco-android] plugin to each module in the project and their respective build variants.
 * The generated xml reports are available under [build/jacoco/jacoco.xml] for each module.
 * These xml reports are sent to [SonarQube] to be displayed under Coverage section on Dashboard.
 * @see <a href="https://sonarcloud.io/dashboard?id=nikhil-thakkar_multi-module-dependency-setup">Example</a>
 */
internal fun Project.configureJacoco() {
    plugins.apply("com.hiya.jacoco-android")

    extensions.getByType<JacocoPluginExtension>().run {
        toolVersion = "0.8.5"
    }

    tasks.withType<Test>().run {
        all {
            configure<JacocoTaskExtension>() {
                isIncludeNoLocationClasses = true
            }
        }
    }

    //Exclude androidx databinding files
    extensions.getByType<JacocoAndroidUnitTestReportExtension>().run {
        excludes = excludes + listOf(
                "androidx/databinding/**/*.class",
                "**/androidx/databinding/*Binding.class",
                "**/**Bind**/**"
        )
    }

    /*
     * The [com.hiya.jacoco-android] plugin doesn't add the code coverage execution data for Instrumentation tests which are generated by running
     * [connectedDebugAndroidTest] or [connectedAndroidTest]
     * Issue open https://github.com/autonomousapps/jacoco-android-gradle-plugin/issues/1
     * TODO: Remove this block once the issue is fixed
     */
    afterEvaluate {
        tasks.withType<JacocoReport>().run {
            all {
                val tree = fileTree(buildDir)
                tree.include("**/*.ec")
                executionData(tree)
            }
        }
    }
}

/**
 * [SonarQube] is the leading tool for continuously inspecting the Code Quality and Security of your codebases and
 * guiding development teams during Code Reviews.
 * These analyses are driven by automated Static Code Analysis rules. You can add your own rules or use [detekt] rules for analysis as well.
 * This method will configure [SonarQube] at the root of the project.
 * Update the values for [sonar.projectKey], [sonar.organization], [sonar.host.url], [sonar.login] according to your project setup.
 */
internal fun Project.configureSonar() {
    rootProject.plugins.apply("org.sonarqube")
    rootProject.extensions.getByType<SonarQubeExtension>().run {
        properties {
            property("sonar.projectKey", "vipulshah2010_FoodApp")
            property("sonar.organization", "vipulshah2010")
            property("sonar.sources", "src/main/java")
            property("sonar.sources.coveragePlugin", "jacoco")
            property("sonar.host.url", "https://sonarcloud.io/")
            property("sonar.exclusions", "**/*.js,**/test/**, buildSrc/*")
            property("sonar.login", "db0bba542c32464fd6996ee6c74622e080a9aa4d")
        }
    }

    extensions.getByType<SonarQubeExtension>().run {
        properties {
            property(
                    "sonar.coverage.jacoco.xmlReportPaths",
                    "${buildDir}/jacoco/jacoco.xml"
            )
        }
    }
}

fun TestOptions.UnitTestOptions.all(block: Test.() -> Unit) =
        all(KotlinClosure1<Any, Test>({ (this as Test).apply(block) }, owner = this))