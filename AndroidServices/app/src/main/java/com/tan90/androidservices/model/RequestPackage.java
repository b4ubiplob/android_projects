package com.tan90.androidservices.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by I320626 on 7/13/2016.
 */
public class RequestPackage {

    private String uri;
    private String method;
    private Map<String, String> params;

    public RequestPackage() {
        method = "GET";
        params = new HashMap<String, String>();
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void addParam(String key, String value) {
        this.params.put(key, value);
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();
        for (String key : params.keySet()) {
            String value = null;
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(key + "=" + value);
        }
        return  sb.toString();
    }
}
