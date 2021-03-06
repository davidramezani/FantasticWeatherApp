package com.david.fantasticweatherapp.data.models.response;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "WeatherData")
public class WeatherResponse {
  @PrimaryKey(autoGenerate = false)
  @SerializedName("id")
  public int id;
  @SerializedName("base")
  public String base;
  @SerializedName("visibility")
  public int visibility;
  @SerializedName("dt")
  public int dt;
  @SerializedName("timezone")
  public int timezone;
  @SerializedName("locationName")
  public String locationName;
  @SerializedName("name")
  public String name;
  @SerializedName("cod")
  public int cod;

  @SerializedName("coord")
  public Coord coord;
  @SerializedName("weather")
  public List<Weather> weather;
  @SerializedName("main")
  public Main main;
  @SerializedName("wind")
  public Wind wind;
  @SerializedName("clouds")
  public Clouds clouds;
  @SerializedName("sys")
  public Sys sys;

  public static class Coord {
    @SerializedName("lon")
    public double lon;
    @SerializedName("lat")
    public double lat;

    public double getLon() {
      return lon;
    }

    public double getLat() {
      return lat;
    }
  }

  public static class Weather {
    @SerializedName("id")
    public int id;
    @SerializedName("main")
    public String main;
    @SerializedName("description")
    public String description;
    @SerializedName("icon")
    public String icon;
  }

  public static class Main {
    @SerializedName("temp")
    public double temp;
    @SerializedName("feels_like")
    public double feels_like;
    @SerializedName("temp_min")
    public double temp_min;
    @SerializedName("temp_max")
    public double temp_max;
    @SerializedName("pressure")
    public int pressure;
    @SerializedName("humidity")
    public int humidity;
  }

  public static class Wind {
    @SerializedName("speed")
    public double speed;
    @SerializedName("deg")
    public int deg;
  }

  public static class Clouds {
    @SerializedName("all")
    public int all;
  }

  public static class Sys {
    @SerializedName("type")
    public int type;
    @SerializedName("id")
    public int id;
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public int sunrise;
    @SerializedName("sunset")
    public int sunset;
  }

}
