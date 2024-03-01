object Config {

    val sdkVersionCode = Versions.getSdkFullVersionCode()
    val sdkVersionName = Versions.getSdkFullVersionName()

    const val compileSdk = 31
    const val minSdkVersion = 19
    const val targetSdkVersion = 31

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    // Compile options
    const val sourceCompatibility = "17"
    const val targetCompatibility = "17"

    const val jvmToolchain = 17
}