package com.tan90.androidservices;

import android.net.http.*;
import android.util.Base64;

import com.tan90.androidservices.model.RequestPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by I320626 on 7/10/2016.
 */
public class HttpManager {

    public static String getData(String uri) {
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            connection.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (null == sb) {
            return  null;
        }
        else {
            return  sb.toString();
        }
    }

    public static String getData(RequestPackage requestPackage, String username, String password) {
        BufferedReader bufferedReader = null;
        byte[] loginBytes = (username + ":" + password).getBytes();
        StringBuffer loginBuffer = new StringBuffer();
        loginBuffer.append("Basic ");
        loginBuffer.append(Base64.encodeToString(loginBytes, Base64.DEFAULT));

        String uri = requestPackage.getUri();

        if (requestPackage.getMethod().equals("GET")) {
            uri += "?" + requestPackage.getEncodedParams();
        }
        StringBuilder sb = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Authorization", loginBuffer.toString());
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            connection.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (null == sb) {
            return  null;
        }
        else {
            return  sb.toString();
        }
    }
}
