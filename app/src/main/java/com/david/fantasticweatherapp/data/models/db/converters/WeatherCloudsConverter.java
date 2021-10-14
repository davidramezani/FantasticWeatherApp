package com.david.fantasticweatherapp.data.models.db.converters;

import androidx.room.TypeConverter;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WeatherCloudsConverter {
  @TypeConverter
  public String toDataBase(WeatherResponse.Clouds data) {
    return new Gson().toJson(data);
  }

  @TypeConverter
  public WeatherResponse.Clouds fromDataBase(String data) {
    Type type = new TypeToken<WeatherResponse.Clouds>() {
    }.getType();
    return new Gson().fromJson(data, type);
  }
}
