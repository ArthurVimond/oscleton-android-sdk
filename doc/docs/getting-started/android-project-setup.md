# Android project setup

### Dependency

Include the dependency in your module's `build.gradle` file:

``` groovy
dependencies {
   compile "com.oscleton.sdk:core:0.3.0"
}
```

<span id="initialization">
### Initialization

Before using the APIs, make sure to initialize the SDK at application launch, usually called in a custom Application's `onCreate()` method:

``` kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the SDK
        OscletonSDK.instance.initialize()
    }

}
```

``` java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the SDK
        OscletonSDK.getInstance().initialize();
    }

}
```


and don't forget to include it in the Android `manifest.xml` file as follow:

``` xml
<application
    android:name=".App"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:theme="@style/AppTheme"
    ...>
</application>

```