package com.david.fantasticweatherapp.data.repository;

import com.david.fantasticweatherapp.data.api.WeatherApi;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Response;

public class WeatherRepository {

    private WeatherApi weatherApi;

    @Inject
    public WeatherRepository(WeatherApi weatherApi){
        this.weatherApi = weatherApi;
    }

    public Observable<Response<WeatherResponse>> getCurrentWeatherData(String query, String appId) {
        return weatherApi.getCurrentWeatherData(query,appId,"metric");
    }
}
