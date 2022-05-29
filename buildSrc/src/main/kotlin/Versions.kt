object Versions {

    // SDK version
    private const val sdkMajor = 1
    private const val sdkMinor = 1
    private const val sdkPatch = 0

    fun getSdkFullVersionCode(): Int {
        return sdkMajor * 1_000_000 + sdkMinor * 1000 + sdkPatch
    }

    fun getSdkFullVersionName(): String {
        return "$sdkMajor.$sdkMinor.$sdkPatch"
    }

    const val gradleBuildTools = "4.2.2"
    const val kotlin = "1.5.30"
    const val dokka = "1.6.21"
    const val firebaseCrashlyticsGradlePlugin = "2.8.1"

    const val core = "1.7.0"
    const val appCompat = "1.4.1"
    const val constraintLayout = "2.1.3"
    const val legacySupportV4 = "1.0.0"
    const val material = "1.6.0"
    const val percentLayout = "1.0.0"
    const val recyclerView = "1.0.0"
    const val vectorDrawable = "1.1.0"

    const val wearable = "2.4.0"
    const val playServicesWearable = "17.1.0"

    const val jUnit = "4.13.2"
    const val jUnitExt = "1.1.3"
    const val archCore = "2.1.0"
    const val testCore = "1.4.0"
    const val testCoreKtx = "1.4.0"
    const val testRunner = "1.4.0"
    const val espresso = "3.4.0"
    const val robolectric = "4.6"
    const val truth = "1.1.3"

    const val browser = "1.2.0"

    const val coroutines = "1.6.1"

    const val firebase = "21.0.0"
    const val firebaseCrashlytics = "18.2.10"
    const val firebaseAnalytics = "21.0.0"

    const val glide = "4.9.0"

    const val googleServices = "4.3.10"
    const val googlePlay = "1.8.0"

    const val gson = "2.8.5"

    const val javaOsc = "0.4"

    const val koin = "3.2.0"

    const val lifecycle = "2.2.0"

    const val paging = "2.1.1"

    const val retrofit = "2.6.2"
    const val retrofitAdapterRxJava = "2.3.0"
    const val retrofitConverterGson = "2.9.0"

    const val room = "2.3.0"

    const val rxJava = "2.2.11"
    const val rxAndroid = "2.1.1"
    const val rxKotlin = "2.3.0"
    const val rxPreferences = "2.0.1"

    const val sentry = "5.7.4"

    const val circularReveal = "2.1.0"
    const val expandableLayout = "2.9.2"
    const val materialDialogs = "0.9.6.0"
    const val tapTargetView = "1.12.0"
    const val rippleBackground = "1.0.1"
}