# Oscleton SDK for Android
[![Build Status](https://travis-ci.org/ArthurVimond/oscleton-android-sdk.svg?branch=master)](https://travis-ci.org/ArthurVimond/oscleton-android-sdk)
[![Download](https://api.bintray.com/packages/arthurvimond/oscleton-android-sdk/core/images/download.svg)](https://bintray.com/arthurvimond/oscleton-android-sdk/core/_latestVersion)

Ableton Live companion SDK for Android

## Official documentation

The official documentation is hosted on the [Oscleton SDK website](https://sdk.oscleton.com).

## Gradle Setup

Check that you have the `jcenter` repository in your Project's `build.gradle` file:

```groovy
repositories {
    // ...
    jcenter()
}
```

Add the base dependency in your Module's `build.gradle` file:

```groovy
dependencies {
    // Base
    implementation 'com.oscleton.sdk:core:1.0.0'
}
```

Additionally to the 'core' artifact, add the 'core-rxjava2' artifact in order to listen for Live data changes with RxJava streams:

```groovy
dependencies {
    implementation 'com.oscleton.sdk:core:1.0.0'
    implementation 'com.oscleton.sdk:core-rxjava2:1.0.0'
}
```

If you prefer to use callbacks, add the 'core-callbacks' artifact in order to listen for Live data changes with callbacks:

```groovy
dependencies {
    implementation 'com.oscleton.sdk:core:1.0.0'
    implementation 'com.oscleton.sdk:core-callbacks:1.0.0'
}
```

## License

```
Copyright 2018-2022 Arthur VIMOND

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```