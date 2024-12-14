# General Android Component Rules
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.app.Application
-keep public class * extends androidx.fragment.app.Fragment

# Parcelable classes
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Preserve annotations
-keepattributes *Annotation*

# Retrofit and OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Preserve Retrofit annotations
-keepattributes RuntimeVisibleAnnotations

# Chucker
-dontwarn com.chuckerteam.chucker.**
-keep class com.chuckerteam.chucker.** { *; }

# Gson (used with Retrofit)
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }
-keepattributes *Annotation*

# Room Database
-dontwarn androidx.room.**
-keep class androidx.room.** { *; }
-keepclassmembers class androidx.room.** {
    *;
}
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keepclassmembers class * {
    @androidx.room.** <methods>;
}
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }

# RxJava
-dontwarn io.reactivex.rxjava3.**
-keep class io.reactivex.rxjava3.** { *; }
-keepclassmembers class io.reactivex.rxjava3.** {
    *;
}

-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keep class io.reactivex.rxjava3.** { *; }

# Hilt (Dagger)
-dontwarn dagger.**
-dontwarn javax.inject.**
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keepattributes *Annotation*

# NetworkBoundResource or Protocol
-dontwarn com.avwaveaf.fleetifyreport.core.data.**
-keepclassmembers class com.avwaveaf.fleetifyreport.core.data.** {
    <fields>;
    <methods>;
}

# Lombok
-dontwarn lombok.bytecode.**
-dontwarn lombok.core.**
-dontwarn lombok.delombok.**
-dontwarn lombok.eclipse.**
-dontwarn lombok.installer.**
-dontwarn lombok.javac.**
-dontwarn lombok.**
-dontwarn lombok.eclipse.handlers.**
-dontwarn lombok.javac.handlers.**
-dontwarn lombok.eclipse.handlers.singulars.**
-dontwarn lombok.javac.handlers.singulars.**

# Detailed Lombok handlers
-dontwarn lombok.bytecode.PoolConstantsApp
-dontwarn lombok.bytecode.PostCompilerApp
-dontwarn lombok.bytecode.PreventNullAnalysisRemover
-dontwarn lombok.core.Main$LicenseApp
-dontwarn lombok.core.Main$VersionApp
-dontwarn lombok.core.PublicApiCreatorApp
-dontwarn lombok.core.configuration.ConfigurationApp
-dontwarn lombok.core.handlers.SneakyThrowsAndCleanupDependencyInfo
-dontwarn lombok.core.runtimeDependencies.CreateLombokRuntimeApp
-dontwarn lombok.delombok.DelombokApp
-dontwarn lombok.eclipse.agent.MavenEcjBootstrapApp
-keep class lombok.** { *; }
-keepattributes *Annotation*

# Keep all classes using annotations for Hilt and Room
-keep class ** {
    @dagger.hilt.android.lifecycle.HiltViewModel *;
    @androidx.room.Database *;
    @androidx.room.Entity *;
    @androidx.room.Dao *;
}

# Final Optimizations
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepclassmembers class ** {
    @androidx.annotation.Keep *;
}

# Entity and UseCase Classes
-keepclassmembers class com.avwaveaf.fleetifyreport.core.domain.entity.** {
    <fields>;
    <methods>;
}
-keep class com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.** { *; }
-keepclassmembers class com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.** {
    <fields>;
    <methods>;
}

# Prevent code stripping for ViewModel LiveData methods
-keepclassmembers class androidx.lifecycle.ViewModel {
    <methods>;
}

# Keep inner classes and enclosing methods
-keepattributes EnclosingMethod
-keepattributes InnerClasses

