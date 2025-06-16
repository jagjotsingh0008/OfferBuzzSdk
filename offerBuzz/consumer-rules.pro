# ────────────────
# OfferBuzz Core
# ────────────────
-keep class com.offerbuzz.ads.** { *; }
-keep interface com.offerbuzz.ads.** { *; }

# ────────────────
# WebView & Javascript Interface
# ────────────────
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# ────────────────
# Retrofit & OkHttp
# ────────────────
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keepattributes Signature
-keepattributes RuntimeVisibleAnnotations

# ────────────────
# Google Play Ads Identifier
# (consumer must add dependency)
# ────────────────
-dontwarn com.google.android.gms.ads.identifier.**

# ────────────────
# Chrome Custom Tabs
# ────────────────
-keep class androidx.browser.customtabs.** { *; }

# ────────────────
# Coroutines
# ────────────────
-dontwarn kotlinx.coroutines.**

# ────────────────
# ViewBinding
# (optional if SDK uses it)
# ────────────────
-keep class androidx.viewbinding.** { *; }
