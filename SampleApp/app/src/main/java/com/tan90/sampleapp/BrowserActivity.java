package com.tan90.sampleapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by I320626 on 11/13/2015.
 */
public class BrowserActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_browser);
        Intent intent = getIntent();
        WebView webView = (WebView) findViewById(R.id.webView);

        String action = intent.getAction();
        if (!action.equals(Intent.ACTION_VIEW)) {
            throw new RuntimeException("Should not happen");
        }

        Uri data = intent.getData();
        URL url;
        try {
            url = new URL(data.getScheme(), data.getHost(), data.getPath());
            webView.loadUrl(url.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
