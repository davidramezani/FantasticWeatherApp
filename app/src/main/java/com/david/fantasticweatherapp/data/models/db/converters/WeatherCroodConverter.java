package com.david.fantasticweatherapp.data.models.db.converters;

import androidx.room.TypeConverter;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class WeatherCroodConverter {
  @TypeConverter
  public String toDataBase(WeatherResponse.Coord data) {
    return new Gson().toJson(data);
  }

  @TypeConverter
  public WeatherResponse.Coord fromDataBase(String data) {
    Type type = new TypeToken<WeatherResponse.Coord>() {
    }.getType();
    return new Gson().fromJson(data, type);
  }
}
