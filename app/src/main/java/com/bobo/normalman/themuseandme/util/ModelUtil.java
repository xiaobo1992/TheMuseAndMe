package com.bobo.normalman.themuseandme.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by xiaobozhang on 10/23/17.
 */

public class ModelUtil {
    static Gson gson = new Gson();
    public static String KEY_MODEL = "MODEL";

    public static <T> void save(Context context, String key, T obj) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(KEY_MODEL, Context.MODE_PRIVATE);
        String val = gson.toJson(obj, new TypeToken<T>() {
        }.getType());
        sp.edit().putString(key, val).apply();
    }

    public static <T> T read(Context context, String key, TypeToken<T> token) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(KEY_MODEL, Context.MODE_PRIVATE);
        try {
            String val = sp.getString(key, "");
            return gson.fromJson(val, token.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toString(Object obj, TypeToken<T> token) {
        return gson.toJson(obj, token.getType());
    }

    public static <T> T toObject(String json, TypeToken<T> token) {
        return gson.fromJson(json, token.getType());
    }
}
