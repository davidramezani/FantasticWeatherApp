package com.david.fantasticweatherapp.data.models.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.david.fantasticweatherapp.data.models.db.tables.LocationData;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface WeatherDao {

  @Query("SELECT * FROM WeatherData WHERE locationName=:cityName")
  Flowable<List<WeatherResponse>> getWeatherData(String cityName);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Completable insertWeatherData(WeatherResponse weatherData);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  Completable insertLocation(LocationData locationData);

  @Query("SELECT * FROM Location WHERE 1 LIMIT 1")
  Flowable<List<LocationData>> getLocationData();

}
