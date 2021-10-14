package com.david.fantasticweatherapp.data.models.db.converters;

import androidx.room.TypeConverter;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;

public class WeatherConverter {

  @TypeConverter
  public String toDataBase(List<WeatherResponse.Weather> data) {
    return new Gson().toJson(data);
  }

  @TypeConverter
  public List<WeatherResponse.Weather> fromDataBase(String data) {
    Type type = new TypeToken<List<WeatherResponse.Weather>>() {
    }.getType();
    return new Gson().fromJson(data, type);
  }

}
