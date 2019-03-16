abstract class FlutterMsalClientApplication {
  String _config;
  String get authenticationConfig => _config;

  FlutterMsalClientApplication(this._config);
}