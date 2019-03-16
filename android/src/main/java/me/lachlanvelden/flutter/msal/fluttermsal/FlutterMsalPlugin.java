package me.lachlanvelden.flutter.msal.fluttermsal;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * FlutterMsalPlugin
 */
public class FlutterMsalPlugin implements MethodCallHandler {

    private static Activity mainActivity;
    private static  Registrar registrar;
    public static HashMap<UUID, MsalCallback> callbacks = new HashMap<UUID, MsalCallback>();
    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_msal");
        channel.setMethodCallHandler(new FlutterMsalPlugin());
        FlutterMsalPlugin.registrar = registrar;
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        ArrayList<Object> arguments = (ArrayList<Object>) call.arguments;
        switch (call.method) {
            case "getPlatformVersion":
                result.success("Android " + android.os.Build.VERSION.RELEASE);
                break;
            case "createPublicClientApplication":
                createPublicApplicationClient(result, (String)call.argument("config"));
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private void createPublicApplicationClient(final Result result, String config) {
        // Create request
        UUID requestId = UUID.randomUUID();
        MsalCallback callback = new MsalCallback() {
            @Override
            public void callback(Boolean success, String token) {
                if(success)
                    result.success(token);
                else
                    result.error("Failed", "failed", new Exception("Request Failed"));
            }
        };

        callbacks.put(requestId, callback);
        mainActivity = registrar.activity();
        Intent intent = new Intent(mainActivity, FlutterMsalActivity.class);
        intent.putExtra("config", config);
        intent.putExtra("requestId", requestId.toString());
        mainActivity.startActivity(intent);
    }

}
