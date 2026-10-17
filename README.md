# Khmer Keyboard for Android

A minimal Android Input Method Editor (IME) with a direct Khmer Unicode key layout. GitHub Actions builds a debug APK when you manually run the workflow.

## What this project does

- Provides a Khmer Unicode keyboard for typing into other Android apps.
- Has Khmer consonant, vowel, diacritic and punctuation rows.
- Includes backspace, space, enter, and a system keyboard switch action.
- Can use a custom font for the keyboard's key labels.
- Builds an APK using GitHub Actions `workflow_dispatch`.

## Important font note

No font file is bundled. Add a font that you own or are licensed to redistribute at:

`app/src/main/assets/fonts/KhmerCustom.ttf`

Create the `assets/fonts` folders if needed. If the file is absent, the keyboard falls back to Android's default typeface. The font setting changes the labels displayed on the keyboard only. It does **not** force other apps to display typed text in that font; the target app and Android system control the appearance of entered text.

The keyboard sends Unicode text. Make sure the font supports Khmer Unicode and has a license that permits your intended use.

## Build the APK on GitHub (phone or computer)

1. Sign in to GitHub and create a **new repository**. For example, name it `khmer-keyboard`.
2. Upload the files and folders from this project ZIP into the repository root. Keep the `.github/workflows/build-apk.yml` path exactly as shown. If GitHub's web uploader hides dot-folders, create `.github/workflows/` in the repository using **Add file → Create new file**, then create `build-apk.yml` and paste the workflow contents from this project.
3. Open the repository's **Actions** tab.
4. If prompted, enable Actions for the repository.
5. Select **Build Khmer Keyboard APK** in the workflow list.
6. Tap **Run workflow**, select the branch (usually `main`), then tap the green **Run workflow** button.
7. Wait for the run to finish with a green check mark. Open that run.
8. Under **Artifacts**, tap `khmer-keyboard-debug-apk` to download the ZIP artifact.
9. Extract the downloaded artifact ZIP. It contains `app-debug.apk`.

## Install on your Android phone

1. Download `app-debug.apk` to your phone.
2. Open it from Downloads or your file manager.
3. If Android asks, allow your browser or file manager to **Install unknown apps** for this installation.
4. Confirm **Install**.
5. Open **Khmer Keyboard**.
6. Tap **Open keyboard settings**, enable **Khmer Keyboard**, then return to the app.
7. Tap **Choose keyboard** and select **Khmer Keyboard**.
8. Open any app with a text field and test typing Khmer.

Only install APKs you built yourself or trust. The debug APK is not intended for Play Store distribution.

## Rebuild after changing the keyboard

Edit the source files in GitHub, commit the changes, then repeat steps 3–9. This workflow runs only when manually triggered; it does not run automatically on every push.

## Project structure

```text
.
├── .github/workflows/build-apk.yml
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/khmerkeyboard/
│       │   ├── MainActivity.java
│       │   └── KhmerImeService.java
│       └── res/
│           ├── values/strings.xml
│           └── xml/method.xml
├── build.gradle
├── gradle.properties
└── settings.gradle
```

## Troubleshooting

- **No Actions tab or workflow:** make sure `.github/workflows/build-apk.yml` is committed to the default branch and Actions are enabled.
- **Build fails:** open the workflow run and inspect the failed step's log. This project uses JDK 17, Gradle 8.9, Android Gradle Plugin 8.7.3, and Android SDK 35.
- **Keyboard doesn't appear:** enable it in Android Settings → System/General management → Keyboard list and default (wording varies by Android version), then select it from the keyboard picker.
- **Khmer text looks wrong:** confirm the target app and system support Khmer Unicode. Add a licensed Khmer Unicode font to customize keyboard labels.
- **English switch key:** this requests the next enabled Android keyboard. Enable another keyboard (such as Gboard) in system settings first.

## Security and privacy

This starter project has no Internet permission and does not transmit keystrokes. Review source code before installing or distributing any modified build.
