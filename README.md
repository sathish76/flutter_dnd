# flutter_dnd
[![pub package](https://img.shields.io/pub/v/flutter_dnd.svg)](https://pub.dartlang.org/packages/flutter_dnd)
A Flutter plugin to manage Do Not Disturb settings on Android.

## Usage
To use this plugin, add `flutter_dnd` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).

Make sure you add the following permissions to your Android Manifest
```
<uses-permission android:name="android.permission.ACCESS_NOTIFICATION_POLICY"/>
```

## Example
``` dart
// Import package
import 'package:flutter_dnd/flutter_dnd.dart';

if (await FlutterDnd.isNotificationPolicyAccessGranted) {
	await FlutterDnd.setInterruptionFilter(FlutterDnd.INTERRUPTION_FILTER_NONE); // Turn on DND - All notifications are suppressed.
} else {
	FlutterDnd.gotoPolicySettings();
}
```

### Other filters

| Filter  | Description  |
| ------------ | ------------ |
| INTERRUPTION_FILTER_ALL | No notifications are suppressed. |
| INTERRUPTION_FILTER_PRIORITY | Allow priority notifications. |
| INTERRUPTION_FILTER_NONE | Suppress all notifications. |
| INTERRUPTION_FILTER_ALARMS | Allow alarm notifications. |

