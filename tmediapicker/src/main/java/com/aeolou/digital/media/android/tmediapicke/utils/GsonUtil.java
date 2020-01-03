package com.aeolou.digital.media.android.tmediapicke.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by huai on 2019/3/19 0021.
 */

public class GsonUtil {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        List<T> list = new ArrayList<>();

        try {
            ArrayList<JsonObject> jsonObjects = new Gson().fromJson(gsonString, type);
            for (JsonObject jsonObject : jsonObjects) {
                list.add(new Gson().fromJson(jsonObject, cls));
            }
        } catch (Exception e) {

        }
        return list;


//            List<T> list = null;
//            if (gson != null) {
//                list = gson.fromJson(gsonString, new TypeToken<List<T>>() {}.getType());
//            }
//            return list;

    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * json转成Map<String,T>
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString, Class<T> vClazz) {
        Map<String, T> map = null;
        if (gson != null) {
            map= gson.fromJson(gsonString, TypeToken.getParameterized(Map.class,String.class,vClazz).getType());

        }
        return map;
    }

    /**
     * json转Map<K,V>
     * @param json
     * @param kClazz
     * @param vClazz
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map<K, V> toMap(String json, Class<K> kClazz, Class<V> vClazz) {
        return gson.fromJson(json, TypeToken.getParameterized(Map.class, kClazz, vClazz).getType());
    }

    /**
     * json转成Map<String,T>
     * @param json
     * @return
     */
    public <V> Map<String, V> toStringKeyMap(String json, Class<V> vClazz) {
        Map<String, V> map = new HashMap<>();
        try {
            JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String entryKey = entry.getKey();
                JsonObject object = (JsonObject) entry.getValue();
                V value = gson.fromJson(object, vClazz);
                map.put(entryKey, value);
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }

}
