package com.monitor.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.UUID;

/**
 * @Author: xymj
 * @Date: 2019/6/22 0022 22:39
 * @Description:
 */
public class CommonUtils {

    private static final Gson GSON = new Gson();

    public static String toJson(Object o) {


        return GSON.toJson(o);
    }

    public static Object fromJson(String json,Type type) {
        return GSON.fromJson(json, type);
    }

    public static String uuidKey() {
        return UUID.randomUUID().toString().replace("-","");
    }

}
