package com.kankan.util;

import com.google.gson.Gson;

public class GsonUtil {

    private static final Gson gson = new Gson();

    public static <T> String toGson(T data) {
        if (data == null) {
            return null;
        }
        return gson.toJson(data);
    }

    public static <T> T parseJson(String json, Class<T> t) {
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, t);
    }


}
