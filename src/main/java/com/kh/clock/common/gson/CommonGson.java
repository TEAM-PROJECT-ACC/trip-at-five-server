package com.kh.clock.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CommonGson {
  
  public static Gson getDateFormattedGson(String dateFormat) {
    return new GsonBuilder().setDateFormat(dateFormat).create();
  }
 
  public static boolean getJsonBoolean(JsonObject obj, String key) {
    return obj.has(key) && obj.get(key) != null && !obj.get(key).isJsonNull() ?
            obj.get(key).getAsBoolean() : false;
  }
  
  public static String getJsonString(JsonObject obj, String key) {
    return obj.has(key) && obj.get(key) != null && !obj.get(key).isJsonNull() ?
            obj.get(key).getAsString() : "-";
  }
  
  public static int getJsonInt(JsonObject obj, String key) {
    return obj.has(key) && obj.get(key) != null && !obj.get(key).isJsonNull() ?
            obj.get(key).getAsInt() : 0;
  }
}
