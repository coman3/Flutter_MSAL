import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_msal/FlutterMsalClientApplication.dart';
import 'package:flutter_msal/FlutterMsalClientApplication.implementation.dart';

class FlutterMsal {
  static const MethodChannel _channel = const MethodChannel('flutter_msal');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> createPublicClientApplication(String config) async {
    try {
      var success = await _channel.invokeMethod<String>(
          'createPublicClientApplication', {"config": config});
      return success;
    } catch (e) {
      print("Login Failed");
      return null;
    }

  }
}
