package com.tan90.androidservices.parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tan90.androidservices.model.Flower;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by I320626 on 7/11/2016.
 */
public class FlowerJsonParser {

    public static List<Flower> parseFeed(String content) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Flower>>(){}.getType();
        return  (List<Flower>) gson.fromJson(content, listType);
    }
}
