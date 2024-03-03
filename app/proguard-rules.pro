# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.google.protobuf.java_com_google_android_gmscore_sdk_target_granule__proguard_group_gtm_N1281923064GeneratedExtensionRegistryLite**
-keep public class com.google.android.gms.** { public protected *; }
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep class com.google.firebase.database.** {*;}



-keep class com.facebook.** { *; }
-dontwarn com.facebook.**
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-keepattributes Synthetic,InnerClasses,EnclosingMethod

# If you're using the Facebook Audience Network, add these rules:
-keep class com.facebook.ads.** { *; }
-dontwarn com.facebook.ads.**

# If you're using Facebook Login, add these rules:
-keep class com.facebook.login.** { *; }
-dontwarn com.facebook.login.**

# If you're using Facebook Share, add these rules:
-keep class com.facebook.share.** { *; }
-dontwarn com.facebook.share.**

# If you're using Facebook Places, add these rules:
-keep class com.facebook.places.** { *; }
-dontwarn com.facebook.places.**

# If you're using Facebook Messenger, add these rules:
-keep class bolts.** { *; }
-dontwarn bolts.**

# If you're using Facebook Graph API, add these rules:
-keep class com.facebook.GraphRequest { *; }
-keep class com.facebook.GraphResponse { *; }
-keep class com.facebook.GraphRequestAsyncTask { *; }

# Keep your custom models and data classes if you have any
-keep class your.package.name.model.** { *; }
