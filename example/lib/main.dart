import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_msal/flutter_msal.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await FlutterMsal.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  Future<void> performLogin() async {
    var result = await FlutterMsal.createPublicClientApplication("""
    {
      "client_id" : "e1d63f16-b34b-4bb4-a251-e3b69b5de374",
      "authorization_user_agent" : "DEFAULT",
      "redirect_uri" : "msale1d63f16-b34b-4bb4-a251-e3b69b5de374://auth",
      "authorities" : [
        {
          "type": "AAD",
          "audience": {
            "type": "AzureADMyOrg",
            "tenant_id": "15864aef-67b6-4b0e-8bfe-cd61201a6837"
          }
        }
      ]
    }    
    """);
    setState(() {
      _platformVersion = result;
    });
    print(result);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Row(
            children: <Widget>[
              Text(_platformVersion),
              RaisedButton(
                child: Text("Perform Login"),
                onPressed: this.performLogin,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
