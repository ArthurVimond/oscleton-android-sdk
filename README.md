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

Add the dependency in your Module's `build.gradle` file:

```groovy
dependencies {
    // ...
    implementation 'fr.arthurvimond.oscletonsdk:core:0.3.0'
}
```

## License

```
Copyright 2018 Arthur VIMOND

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