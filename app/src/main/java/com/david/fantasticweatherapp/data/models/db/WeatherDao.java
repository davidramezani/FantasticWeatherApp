package com.david.fantasticweatherapp.data.models.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;

@Dao
public interface WeatherDao {

  @Query("SELECT * FROM WeatherData WHERE name=:cityName")
  WeatherResponse getWeatherData(String cityName);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void setWeatherData(WeatherResponse weatherData);

}
