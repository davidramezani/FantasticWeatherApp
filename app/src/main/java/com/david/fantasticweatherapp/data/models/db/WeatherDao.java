package com.david.fantasticweatherapp.data.models.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface WeatherDao {

  @Query("SELECT * FROM WeatherData WHERE name=:cityName")
  Flowable<List<WeatherResponse>> getWeatherData(String cityName);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Completable insertWeatherData(WeatherResponse weatherData);

}
