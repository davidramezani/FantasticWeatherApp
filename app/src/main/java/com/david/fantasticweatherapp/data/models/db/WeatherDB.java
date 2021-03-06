package com.david.fantasticweatherapp.data.models.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.david.fantasticweatherapp.data.models.db.converters.WeatherCloudsConverter;
import com.david.fantasticweatherapp.data.models.db.converters.WeatherConverter;
import com.david.fantasticweatherapp.data.models.db.converters.WeatherCroodConverter;
import com.david.fantasticweatherapp.data.models.db.converters.WeatherMainConverter;
import com.david.fantasticweatherapp.data.models.db.converters.WeatherSysConverter;
import com.david.fantasticweatherapp.data.models.db.converters.WeatherWindConverter;
import com.david.fantasticweatherapp.data.models.db.tables.LocationData;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;

@Database(entities = {WeatherResponse.class, LocationData.class}, version = 1)
@TypeConverters(
  {
    WeatherCroodConverter.class,
    WeatherConverter.class,
    WeatherMainConverter.class,
    WeatherCloudsConverter.class,
    WeatherWindConverter.class,
    WeatherSysConverter.class
  })
public abstract class WeatherDB extends RoomDatabase {
  public abstract WeatherDao weatherDao();
}
