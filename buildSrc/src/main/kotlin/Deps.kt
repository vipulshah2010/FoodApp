object Deps {

    /********************** Versions **********************/

    // kotlin
    private const val kotlinVersion = "1.3.72"
    private const val coroutinesVersion = "1.3.7"

    // AndroidX
    private const val coreVersion = "1.3.0"
    private const val fragmentVersion = "1.2.5"
    private const val lifecycleVersion = "2.2.0"
    private const val navigationVersion = "2.2.2"

    // hilt
    private const val hiltVersion = "2.28.1-alpha"
    private const val hiltViewModelVersion = "1.0.0-alpha01"

    // room
    private const val roomVersion = "2.2.5"

    // retrofit
    private const val retrofitVersion = "2.9.0"
    private const val interceptorVersion = "4.8.0"

    // generic
    private const val epoxyVersion = "3.11.0"
    private const val coilVersion = "0.11.0"

    // test
    private const val testCore = "1.2.0"
    private const val testEspressoVersion = "3.2.0"
    private const val testJU5Version = "5.6.2"
    private const val truthVersion = "1.0.1"
    private const val turbineVersion = "0.1.1"

    /********************** Dependencies **********************/

    // kotlin
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"

    // androidx
    const val core = "androidx.core:core:$coreVersion"
    const val coreKtx = "androidx.core:core-ktx:$coreVersion"
    const val fragment = "androidx.fragment:fragment:$fragmentVersion"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:$lifecycleVersion"
    const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    const val navigationUI = "androidx.navigation:navigation-ui-ktx:$navigationVersion"

    // hilt
    const val hilt = "com.google.dagger:hilt-android:$hiltVersion"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    const val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$hiltViewModelVersion"
    const val hiltViewModelCompiler = "androidx.hilt:hilt-compiler:$hiltViewModelVersion"

    // room
    const val room = "androidx.room:room-runtime:$roomVersion"
    const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
    const val roomKtx = "androidx.room:room-ktx:$roomVersion"

    // ui
    const val cardView = "androidx.cardview:cardview:1.0.0"
    const val circleImage = "de.hdodenhof:circleimageview:3.1.0"
    const val constraint = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val material = "com.google.android.material:material:1.2.0-rc01"
    const val skeletonLoading = "com.facebook.shimmer:shimmer:0.5.0"

    // retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val converter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    const val retrofitInterceptor = "com.squareup.okhttp3:logging-interceptor:$interceptorVersion"

    // generic
    const val commonsLang = "org.apache.commons:commons-lang3:3.10"
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val coil = "io.coil-kt:coil:$coilVersion"

    // test
    const val fragmentTesting = "androidx.fragment:fragment-testing:$fragmentVersion"
    const val archCoreTesting = "androidx.arch.core:core-testing:2.1.0"
    const val testCoreDep = "androidx.test:core:$testCore"
    const val espresso = "androidx.test.espresso:espresso-core:$testEspressoVersion"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:$testEspressoVersion"
    const val espressoIdling = "androidx.test.espresso.idling:idling-concurrent:$testEspressoVersion"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:$testEspressoVersion"

    const val jU5api = "org.junit.jupiter:junit-jupiter-api:$testJU5Version"
    const val jU5Engine = "org.junit.jupiter:junit-jupiter-engine:$testJU5Version"
    const val jU5TestCore = "de.mannodermaus.junit5:android-test-core:$testCore"
    const val jU5TestRunner = "de.mannodermaus.junit5:android-test-runner:$testCore"

    const val jUnitExt = "androidx.test.ext:junit:1.1.1"
    const val mockk = "io.mockk:mockk:1.10.0"
    const val orchestrator = "androidx.test:orchestrator:$testCore"
    const val rules = "androidx.test:rules:$testCore"
    const val runner = "androidx.test:runner:$testCore"
    const val uiAutomator = "androidx.test.uiautomator:uiautomator:2.2.0"
    const val roomTesting = "androidx.room:room-testing:$roomVersion"
    const val truth = "com.google.truth:truth:$truthVersion"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"

}