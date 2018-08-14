# Controller

[Controller](../api-reference/fr.arthurvimond.oscletonsdk/-controller/) allows you control multiple parts of Ableton Live from your Android device.

As an example, the following snippet lets you start playing the current Live set:

``` kotlin
OscletonSDK.instance.controller.play()
```

``` java
OscletonSDK.getInstance().getController().play();
```