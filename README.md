# Privacy Awareness Application

## Installation Guide (Windows)

This guide explains how to install and configure the software required to run and test the Privacy Awareness Application on Windows.

## Step 1: Install Python and uv

### Prerequisites

Before getting started, install [Visual Studio Code](https://code.visualstudio.com/). Python can be installed separately from [python.org](https://www.python.org/downloads/), but this is optional because **uv can automatically install a compatible Python version**.

The backend requires **Python 3.14 or newer**.

### Install uv

This project uses [uv](https://docs.astral.sh/uv/) to manage Python versions, virtual environments, and project dependencies.

Open the terminal in VS Code (**Terminal → New Terminal**) and run:

```powershell
winget install --id astral-sh.uv -e
```

After installation, restart VS Code, open a new terminal, and verify the installation:

```powershell
uv --version
```

If a version number appears, uv is ready to use.

### Install project dependencies

Download the project from GitHub, extract it if necessary, and open the **Backend** folder in VS Code.

Open a new terminal and run:

```powershell
uv sync
```

This command finds or downloads a compatible Python version (3.14 or newer), creates a virtual environment, and installs the required Python packages.

## Step 2: Install Android Studio

Android Studio is required to set up the Android emulator used to run and test the application.

### Download and install Android Studio

Visit the [official Android Studio website](https://developer.android.com/studio) and click **Download Android Studio**.

Open the downloaded installer and follow the installation instructions. Select **Standard** installation if prompted, and allow Android Studio to install the required Android SDK components.

### Set up the Android emulator

After installing Android Studio, open it and follow these steps:

1. Open **Device Manager** from the right-hand sidebar. If it is not visible, go to **View → Tool Windows → Device Manager**.
2. Click the **+** button and select **Create Virtual Device**.
3. Select **Pixel 10 Pro** and click **Next**.
4. Select **API 36.1 – Android 16 (Baklava)**.
5. Under **System Image**, select **Google Play Intel x86_64 Atom System Image** (API 36.1), **not** the 16 KB Page Size variant.
6. Click **Finish** to create the virtual device.
7. If prompted to install the required system image, click **Yes**.
8. Allow the system image to download and install.
9. Once installation is complete, click **Finish**.

The **Pixel 10 Pro emulator running Android 16 (API 36.1)** will appear in Device Manager.

### Enable Windows Hypervisor Platform

The Android emulator uses hardware virtualization. When launching it for the first time, Android Studio may warn that **Windows Hypervisor Platform is not enabled**.

1. Follow the prompt to enable Windows Hypervisor Platform and click **OK** when requested.
2. Allow Windows to enable the required feature.
3. Restart your computer if prompted.
4. Reopen Android Studio after restarting.

If the emulator still cannot start, check that hardware virtualization is enabled in your computer's BIOS/UEFI settings. See [Android's emulator acceleration guide](https://developer.android.com/studio/run/emulator-acceleration) for details.

## Step 3: Install test applications

To test the Privacy Awareness Application, install these 10 Android applications from the Google Play Store:

1. **Amazon Music**
2. **Amazon Shopping**
3. **Temu**
4. **Just Eat**
5. **LinkedIn**
6. **Reddit**
7. **Ring**
8. **SoundCloud**
9. **Waze**
10. **Brilliant**

### Installation instructions

1. Start the **Pixel 10 Pro emulator** in Android Studio's Device Manager.
2. Open the **Google Play Store** on the emulator.
3. Sign in using your Google account.
4. Search for and install each of the 10 applications listed above.
5. Once all the applications are installed, close the Google Play Store.

The emulator is now prepared for testing the Privacy Awareness Application.

## Step 4: Install Ollama

The project uses [Ollama](https://ollama.com/) to run Google's **Gemma 3 12B** language model locally for AI-powered privacy policy summaries.

### Download and install Ollama

1. Visit the [official Ollama downloads page](https://ollama.com/download).
2. Select **Windows**.
3. Click **Download manually** to download the Windows installer.
4. Open **OllamaSetup.exe**.
5. Follow the installation instructions.

### Verify the installation

In VS Code, open **Terminal → New Terminal** and run:

```powershell
ollama --version
```

If a version number appears, Ollama is installed.

## Step 5: Install Gemma 3 12B

### Download the AI model

In the VS Code terminal, run:

```powershell
ollama pull gemma3:12b
```

This downloads the **Gemma 3 12B** model (approximately 8.1 GB). Allow the download to finish before continuing.

### Verify the model installation

```powershell
ollama list
```

You should see **gemma3:12b** in the list of installed models.

You do not need to start the model manually. The backend will communicate with Ollama when AI-generated privacy policy summaries are requested. Make sure Ollama is running while using the application.

## Step 6: Run the backend server

In VS Code, open the project's **Backend** folder and start a new terminal (**Terminal → New Terminal**).

Run:

```powershell
uv run python -m uvicorn main:app --reload
```

This starts the FastAPI backend with automatic reloading when backend code changes.

Open [http://127.0.0.1:8000/docs](http://127.0.0.1:8000/docs) in your browser to view the interactive API documentation.

**Important:** Keep the backend terminal running while using the Android application.

## Step 7: Run the Android application

With the backend server running, return to Android Studio.

1. Open the project's Android application folder in Android Studio.
2. Allow Gradle to synchronise and download the required dependencies.
3. Open **Device Manager** and start the **Pixel 10 Pro emulator** if it is not already running.
4. Select the Pixel 10 Pro emulator from the device dropdown menu.
5. Click the **Run ▶** button in Android Studio.
6. Wait for the Android application to build and launch on the emulator.

Once the application launches, you can test its features using the 10 applications installed earlier.

**Important:** Keep both the FastAPI backend server and Ollama running while testing the application.
