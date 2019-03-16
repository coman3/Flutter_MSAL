# Flutter_MSAL
A *WIP* Microsoft Authentication Library (MSAL) Flutter implementation

# _Gotchas_
Yup, this package is really new... Nothing has been tested yet and is only just being implemented. _**DO NOT USE IN PRODUCTION**_
 - **Experimental**
 - **Work In Progress**
 - **Breaking Changes Expected**
 - **Android Only _(For Now)_**

# Installing

## Android
1.  Add the following to your `android/app/src/main/AndroidManifest.xml` file to allow the MSAL authentication flow to work. 
    > Add __under__ all content within the `<application>` tags
    ```xml
    <activity
        android:name="me.lachlanvelden.flutter.msal.fluttermsal.FlutterMsalActivity">
    </activity>

    <activity
        android:name="com.microsoft.identity.client.BrowserTabActivity">
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
                
            <data android:scheme="{Your Scheme Url (Example: msal39bb2d8c-4385-4588-b291-82bcab072eaf)}"
                android:host="auth" />
        </intent-filter>
    </activity>
    ```
2. Make sure you have the 2 following permissions in your `android/app/src/main/AndroidManifest.xml` file.
    > Add __on top__ of all content within the `<manifest>` tags
    ```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    ```
3. Update your `minSdkVersion` to `21` in your `android/app/build.gradle` file.
    > Replace `minSdkVersion 16` with `minSdkVersion 21`
    ```
    defaultConfig {
        applicationId ...
        minSdkVersion 21  // <- Change Me
        targetSdkVersion ...
        versionCode flutterVersionCode.toInteger()
        versionName flutterVersionName
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    ```

# Contributing
I dont have a mac, i probably wont be able to implement IOS on my own without major bugs, please help (i beg you, i need this for an app im making too) :)