# WidgetDemoApp


## Overview
This widget visualizes yearly progress in a compact, user-friendly grid:
- 12 columns for the months (January → December)
- Each column shows the days of that month as individual markers (filled/unfilled) so users can quickly see progress through the year at a glance
- Designed for readability on home screen widgets and (where supported) lock screens

## Features
- Yearly progress visualization: 12 months × days-of-month grid
- Lightweight, customizable appearance (colors, sizing, label styles)
- Sample configuration and update logic
- Demonstrates how to add a widget to the home screen and (on supported devices) lock screen

## Lock screen behavior (Important)
- If the app is running on a Samsung phone and the device is using Google Lock (Google's lock screen implementation), the widget will appear on the lock screen.
- Behavior depends on device model, OS version, launcher, and system settings. On some devices you may need to enable lock screen widgets or grant relevant permissions in system settings for the widget to be available on the lock screen.
- Emulators may not accurately represent lock screen behavior; test on a physical device when possible.

Tip: If the widget doesn't appear on the lock screen, check:
- System → Lock screen (or Security & lock screen) settings to enable lock screen widgets
- That the widget is added to your home screen and the host app is installed/updated
- Restarting the device after installation

## Requirements
- Android Studio (latest stable recommended)
- Android SDK (compileSdkVersion/minSdkVersion as configured in the project)
- A physical device or emulator for testing widgets (emulators may have limitations for lockscreen features)

## Installation
1. Clone the repository:
   git clone https://github.com/LilithUwU/WidgetDemoApp.git
2. Open the project in Android Studio.
3. Let Gradle sync and install required SDK components.
4. Build and run the app on a device.

## Usage
- Add the widget to your home screen via the launcher widget picker.
- Configure the widget (if configuration UI is provided) by tapping the widget or using the host app.
- The widget displays the current year's progress with:
  - One column per month
  - One marker per day (month length-aware: Feb 28/29, Apr/Jun/Sep/Nov 30, others 31)
  - Filled markers indicate days passed, empty markers indicate days remaining
- To test lock screen appearance, follow your device's steps for enabling lock screen widgets (see Lock screen behavior above).

## Customization
- Widget layouts and styles are located in the `res/layout` and `res/values` folders.
- Update provider logic in the widget provider/service classes to change update frequency or displayed content.
- The grid rendering logic supports:
  - Color themes (progress color, background, inactive day color)
  - Compact and expanded layouts (adjust cell size, spacing)
  - Optional month labels or abbreviations
- Use RemoteViews to modify widget UI from your update logic. For more advanced visuals, consider using a small Canvas-backed RemoteViews (or shortcut to open the full app for richer visuals).

## Contributing
Contributions are welcome. Please open an issue to discuss major changes or submit a pull request with a clear description and tests where appropriate.

## Contact
Maintainer: LilithUwU
- GitHub: https://github.com/LilithUwU

<img width="404" height="844" alt="image" src="https://github.com/user-attachments/assets/04bba5e0-18b0-4661-abb8-01b62f54dcfa" />
<img width="404" height="844" alt="image" src="https://github.com/user-attachments/assets/d8010c89-6abc-4ecd-be6c-eddd12dce429" />
<img width="404" height="844" alt="image" src="https://github.com/user-attachments/assets/0219656e-b96b-4bdd-8979-e433fb061363" />
