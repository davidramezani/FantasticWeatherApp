package com.david.fantasticweatherapp.data.api;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

  @GET("2.5/weather")
  Observable<Response<WeatherResponse>> getCurrentWeatherData(
    @Query("q") String query,
    @Query("appid") String appId,
    @Query("units") String units
  );

}
