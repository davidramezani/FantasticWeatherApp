package com.david.fantasticweatherapp.data.repository;

import android.util.Log;

import com.david.fantasticweatherapp.data.api.WeatherApi;
import com.david.fantasticweatherapp.data.models.db.WeatherDB;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class WeatherRepository {

    private WeatherApi weatherApi;
    private WeatherDB weatherDB;

    @Inject
    public WeatherRepository(
        WeatherApi weatherApi,
        WeatherDB weatherDB) {
        this.weatherApi = weatherApi;
        this.weatherDB = weatherDB;
    }

    public Observable<Response<WeatherResponse>> getCurrentWeatherData(String query, String appId) {
        return weatherApi.getCurrentWeatherData(query, appId, "metric");
    }

    public Flowable<List<WeatherResponse>> getWeatherData(String cityName) {
        return weatherDB.weatherDao().getWeatherData(cityName);
    }

    public void updateWeatherDataDb(WeatherResponse weatherResponse) {
        weatherDB.weatherDao().insertWeatherData(weatherResponse).subscribeOn(Schedulers.io())
            .subscribe(new Action() {
                @Override
                public void run() throws Throwable {
                    Log.i("insertProcess", "Success");
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Throwable {
                    Log.i("insertProcess", "Failed");
                }
            });
    }
}
