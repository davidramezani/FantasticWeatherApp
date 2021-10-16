package com.david.fantasticweatherapp.services;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Data;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.david.fantasticweatherapp.data.api.WeatherApi;
import com.david.fantasticweatherapp.data.repository.WeatherRepository;
import com.david.fantasticweatherapp.other.Constants;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Single;
import okhttp3.Request;

@HiltWorker
public class UpdateWeatherWorkManager extends RxWorker {

    Context context;
    WeatherRepository weatherRepository;
    WeatherApi weatherApi;

    @AssistedInject
    public UpdateWeatherWorkManager(
        @Assisted @NotNull Context appContext,
        @Assisted @NotNull WorkerParameters workerParameters,
        WeatherRepository weatherRepository,
        WeatherApi weatherApi
    ) {
        super(appContext, workerParameters);
        this.context = appContext;
        this.weatherRepository = weatherRepository;
        this.weatherApi = weatherApi;
    }

    @NonNull
    @Override
    public Single<Result> createWork() {

        String cityName = getInputData().getString("CityName");

        return weatherRepository.getSingleWeatherData(cityName)
            .doOnSuccess(result -> {
                updateWeatherWidget(new Gson().toJson(result.body()));
            })
            .map(weatherResponseResponse -> Result.success())
            .doOnError(throwable -> Result.failure());
    }

    private void updateWeatherWidget(String weatherData) {
        Intent intent = new Intent(context, WeatherAppWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(new ComponentName(context, WeatherAppWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intent.putExtra("WeatherData", weatherData);
        context.sendBroadcast(intent);
    }

    public Single<Result> getWeatherData(String cityName) {
        weatherRepository.getCurrentWeatherData(cityName).map(weatherResponse -> {
            if (weatherResponse.isSuccessful()) {
                Intent intent = new Intent(context, WeatherAppWidgetProvider.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                int[] ids = AppWidgetManager.getInstance(context)
                    .getAppWidgetIds(new ComponentName(context, WeatherAppWidgetProvider.class));
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                context.sendBroadcast(intent);
                return Result.success();
            } else {
                return Single.just(Result.retry());
            }
        });
        return Single.just(Result.success());
    }
}
