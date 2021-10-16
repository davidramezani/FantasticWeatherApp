package com.david.fantasticweatherapp.data.api;

import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.google.gson.JsonObject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
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

  @GET("2.5/weather")
  Single<Response<WeatherResponse>> getSingleWeatherData(
      @Query("q") String query,
      @Query("appid") String appId,
      @Query("units") String units
  );

}
