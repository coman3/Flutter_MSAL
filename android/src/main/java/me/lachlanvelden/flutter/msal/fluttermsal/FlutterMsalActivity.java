package me.lachlanvelden.flutter.msal.fluttermsal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.MsalClientException;
import com.microsoft.identity.client.MsalException;
import com.microsoft.identity.client.MsalServiceException;
import com.microsoft.identity.client.PublicClientApplication;

import java.util.List;
import java.util.UUID;

public class FlutterMsalActivity extends AppCompatActivity {
    private static PublicClientApplication publicClientApplication;
    private UUID requestId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String config = intent.getStringExtra("config");
        Log.d("Configuration", config);
        String requestId = intent.getStringExtra("requestId");
        this.requestId = UUID.fromString(requestId);

        if(publicClientApplication == null) {
            publicClientApplication = new PublicClientApplication(this.getApplicationContext(), config);
        }
        publicClientApplication.acquireToken(this, new String[]{"User.Read"}, this.getAuthInteractiveCallback());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        publicClientApplication.handleInteractiveRequestRedirect(requestCode, resultCode, data);
    }

    private AuthenticationCallback getAuthInteractiveCallback() {
        return new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                /* Successfully got a token, use it to call a protected resource */

                String accessToken = authenticationResult.getAccessToken();
                Log.i("THE ACCESS TOKEENNN!!!!", accessToken);
                FlutterMsalPlugin.callbacks.get(requestId).callback(true, accessToken);
                finish();
            }
            @Override
            public void onError(MsalException exception) {
                /* Failed to acquireToken */

                if (exception instanceof MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception instanceof MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
                FlutterMsalPlugin.callbacks.get(requestId).callback(false, null);
                finish();
            }
            @Override
            public void onCancel() {
                /* User canceled the authentication */
                FlutterMsalPlugin.callbacks.get(requestId).callback(false, null);
                finish();
            }
        };
    }
}
