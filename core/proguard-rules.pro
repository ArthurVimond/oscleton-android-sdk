# Oscleton SDK consumer ProGuard rules

-keepclasseswithmembers class fr.arthurvimond.oscletonsdk.* {
    public <methods>;
}

-keep class fr.arthurvimond.oscletonsdk.models.* { *; }

-keep class fr.arthurvimond.oscletonsdk.listeners.* { *; }

-keepclasseswithmembers class fr.arthurvimond.oscletonsdk.utils.* {
    public <methods>;
}

-keep class fr.arthurvimond.oscletonsdk.exceptions.* { *; }

-keep class fr.arthurvimond.oscletonsdk.enums.* { *; }