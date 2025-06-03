package com.kh.clock.common.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class CommonGson {
  
  public static Gson getDateFormattedGson(String dateFormat) {
    return new GsonBuilder().setDateFormat(dateFormat).create();
  }
}
