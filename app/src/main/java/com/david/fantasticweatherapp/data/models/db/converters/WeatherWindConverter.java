package com.david.fantasticweatherapp.data.models.db.converters;

import androidx.room.TypeConverter;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WeatherWindConverter {
  @TypeConverter
  public String toDataBase(WeatherResponse.Wind data) {
    return new Gson().toJson(data);
  }

  @TypeConverter
  public WeatherResponse.Wind fromDataBase(String data) {
    Type type = new TypeToken<WeatherResponse.Wind>() {
    }.getType();
    return new Gson().fromJson(data, type);
  }
}
