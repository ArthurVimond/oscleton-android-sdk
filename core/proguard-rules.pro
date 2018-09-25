# Oscleton SDK consumer ProGuard rules

-keepclasseswithmembers class com.oscleton.sdk.* {
    public <methods>;
}

-keep class com.oscleton.sdk.models.* { *; }

-keep class com.oscleton.sdk.listeners.* { *; }

-keepclasseswithmembers class com.oscleton.sdk.utils.* {
    public <methods>;
}

-keep class com.oscleton.sdk.exceptions.* { *; }

-keep class com.oscleton.sdk.enums.* { *; }