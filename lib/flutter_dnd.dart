import 'dart:async';

import 'package:flutter/services.dart';

class FlutterDnd {
  static const int INTERRUPTION_FILTER_UNKNOWN = 0;
  static const int INTERRUPTION_FILTER_ALL = 1;
  static const int INTERRUPTION_FILTER_PRIORITY = 2;
  static const int INTERRUPTION_FILTER_NONE = 3;
  static const int INTERRUPTION_FILTER_ALARMS = 4;

  static const MethodChannel _channel = const MethodChannel('flutter_dnd');

  static Future<bool> get isNotificationPolicyAccessGranted async {
    return await _channel.invokeMethod('isNotificationPolicyAccessGranted');
  }

  static void gotoPolicySettings() {
    _channel.invokeMethod('gotoPolicySettings');
  }

  static Future<bool> setInterruptionFilter(int a) async {
    return await _channel.invokeMethod('setInterruptionFilter', a);
  }

  static Future<int> getCurrentInterruptionFilter() async {
    return await _channel.invokeMethod('getCurrentInterruptionFilter');
  }

  static String getFilterName(int filter) {
    switch (filter) {
      case 1:
        return 'INTERRUPTION_FILTER_ALL';
        break;
      case 2:
        return 'INTERRUPTION_FILTER_PRIORITY';
      case 3:
        return 'INTERRUPTION_FILTER_NONE';
      case 4:
        return 'INTERRUPTION_FILTER_ALARMS';
        break;
      default:
        return 'INTERRUPTION_FILTER_UNKNOWN';
    }
  }
}
