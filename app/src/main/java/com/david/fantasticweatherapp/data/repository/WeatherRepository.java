package com.david.fantasticweatherapp.data.repository;

import android.util.Log;

import com.david.fantasticweatherapp.data.api.WeatherApi;
import com.david.fantasticweatherapp.data.models.db.WeatherDB;
import com.david.fantasticweatherapp.data.models.db.tables.LocationData;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.david.fantasticweatherapp.other.Constants;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
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

    public Observable<Response<WeatherResponse>> getCurrentWeatherData(String query) {
        return weatherApi.getCurrentWeatherData(query, Constants.WEATHER_API_ID, "metric");
    }

    public Flowable<List<WeatherResponse>> getWeatherData(String cityName) {
        return weatherDB.weatherDao().getWeatherData(cityName);
    }

    public void updateWeatherDataDb(WeatherResponse weatherResponse) {
        weatherDB.weatherDao().insertWeatherData(weatherResponse).subscribeOn(Schedulers.io())
            .subscribe(
                () -> Log.i("insertProcess", "Success"),
                throwable -> Log.i("insertProcess", "Failed")
            );
    }

    public Flowable<List<LocationData>> getLocation() {
        return weatherDB.weatherDao().getLocationData();
    }

    public void setDefaultLocation() {
        LocationData defLocation = new LocationData(1, Constants.DefaultCity, Constants.DefaultLat, Constants.DefaultLon);
        weatherDB.weatherDao().insertLocation(defLocation)
            .subscribeOn(Schedulers.io())
            .subscribe();
    }

    public void updateLocation(LocationData locationData) {
        weatherDB.weatherDao().insertLocation(locationData)
            .subscribeOn(Schedulers.io())
            .subscribe();
    }
}
