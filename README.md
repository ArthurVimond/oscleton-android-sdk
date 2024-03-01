# Oscleton SDK for Android

Ableton Live companion SDK for Android

## Official documentation

The official documentation is hosted on the [Oscleton SDK website](https://sdk.oscleton.com).

## Gradle Setup

Clone the `oscleton-android-sdk` repository next to your project.

Include the `core` module in your `settings.gradle` file as follow:

```groovy
include ':core'
project(':core').projectDir = new File(rootProject.projectDir, '../oscleton-android-sdk/core')
```

Add the base dependency in your Module's `build.gradle` file:

```groovy
dependencies {
    // Base
    implementation project(":core")
}
```

Additionally to the 'core' module, add the 'core-rxjava2' module in order to listen for Live data changes with RxJava streams:

`settings.gradle` file:
```groovy
include ':core-rxjava2'
project(':core-rxjava2').projectDir = new File(rootProject.projectDir, '../oscleton-android-sdk/core-rxjava2')
```

`build.gradle` file:
```groovy
dependencies {
    implementation project(":core")
    implementation project(":core-rxjava2")
}
```

If you prefer to use callbacks, add the 'core-callbacks' module in order to listen for Live data changes with callbacks:

`settings.gradle` file:
```groovy
include ':core-callbacks'
project(':core-callbacks').projectDir = new File(rootProject.projectDir, '../oscleton-android-sdk/core-callbacks')
```

`build.gradle` file:
```groovy
dependencies {
    implementation project(":core")
    implementation project(":core-callbacks")
}
```

## License

```
Copyright 2018-2024 Arthur VIMOND

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