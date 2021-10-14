package com.david.fantasticweatherapp.data.models.db.converters;

import androidx.room.TypeConverter;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WeatherConverter {

  @TypeConverter
  public String toDataBase(WeatherResponse.Weather data) {
    return new Gson().toJson(data);
  }

  @TypeConverter
  public WeatherResponse.Weather fromDataBase(String data) {
    Type type = new TypeToken<WeatherResponse.Weather>() {
    }.getType();
    return new Gson().fromJson(data, type);
  }

}
