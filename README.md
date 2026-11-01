# Privacy Awareness Application

Android app that scans your installed apps, gives each one a privacy risk level and summarises its privacy policy with AI.

## You need

- [uv](https://docs.astral.sh/uv/)
- [Ollama](https://ollama.com/)
- [Android Studio](https://developer.android.com/studio)

## Run the backend

Download the AI model (only needed once):

```
ollama pull gemma3:12b
```

Start the backend:

```
cd Backend
uv run python src/main.py
```

Leave this running. It runs on port 8000.

## Run the app

This was used with an Android emulator: Pixel 10 Pro, API 36.1.

1. Open the `PrivacyAwarenessApp` folder in Android Studio.
2. Start an emulator that has Google Play.
3. Install some apps from Google Play on the emulator.
4. Press Run.
5. Press Scan Apps.

The app only works on the emulator because it connects to the backend through `10.0.2.2`.

## Policy summaries

Add policy text files to `Backend/src/backend/services/policy_summariser/policies/`, named after the app's package, e.g. `com.waze.txt`. They are not in this repo. Without them the app can't show a summary.

## Run the tests

```
cd Backend
uv run pytest
```
