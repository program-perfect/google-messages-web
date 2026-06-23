# Messages Expressive Android

Native Android messenger simulator built with Kotlin, Jetpack Compose, and Material 3.

The app is intentionally a cinematic simulator, not a real backend messenger. It includes fake conversations, fake chat threads, adaptive phone/tablet layout, dynamic color support on Android 12+, expressive shapes, tonal surfaces, and Material 3 controls.

## Build locally

```bash
gradle -p android :app:assembleDebug
```

The APK will be created at:

```text
android/app/build/outputs/apk/debug/app-debug.apk
```

## GitHub Actions

The workflow `.github/workflows/android-apk.yml` builds the debug APK on pushes to `native-android-m3-expressive`, on PRs that touch the Android project, and via manual `workflow_dispatch`.
