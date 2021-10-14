package com.david.fantasticweatherapp.data.models.db.converters;

import androidx.room.TypeConverter;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WeatherMainConverter {
  @TypeConverter
  public String toDataBase(WeatherResponse.Main data) {
    return new Gson().toJson(data);
  }

  @TypeConverter
  public WeatherResponse.Main fromDataBase(String data) {
    Type type = new TypeToken<WeatherResponse.Main>() {
    }.getType();
    return new Gson().fromJson(data, type);
  }
}
