package com.david.fantasticweatherapp.data.models.response;

import com.david.fantasticweatherapp.data.models.local.WeatherData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("crood")
    public WeatherData.Coord crood;
    @SerializedName("weather")
    public List<WeatherData.Weather> weather;
    @SerializedName("main")
    public WeatherData.Main main;
    @SerializedName("wind")
    public WeatherData.Wind wind;
    @SerializedName("clouds")
    public WeatherData.Clouds clouds;
    @SerializedName("sys")
    public WeatherData.Sys sys;

    @SerializedName("base")
    public String base;
    @SerializedName("visibility")
    public int visibility;
    @SerializedName("dt")
    public int dt;
    @SerializedName("timezone")
    public int timezone;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public int cod;
}
