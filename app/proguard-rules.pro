# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepattributes Signature
-keepattributes *Annotation*

-keeppackagenames org.jsoup.nodes

-keepclassmembers class com.csatimes.dojma.EventItem {
  *;
}
-keepclassmembers class com.csatimes.dojma.GazetteItem {
  *;
}
-keepclassmembers class com.csatimes.dojma.PosterItem {
  *;
}

-keepclassmembers class com.csatimes.dojma.LinkItem {
  *;
}
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry
-keep class com.firebase.** { *; }
-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn org.w3c.dom.**

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**



-dontwarn okhttp3.**

# Works around a bug in the animated GIF module which will be fixed in 0.12.0
-keep class com.facebook.imagepipeline.animated.factory.AnimatedFactoryImpl {
    public AnimatedFactoryImpl(com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory,com.facebook.imagepipeline.core.ExecutorSupplier);
}