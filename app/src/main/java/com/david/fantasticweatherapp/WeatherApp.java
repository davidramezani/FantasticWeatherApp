package com.david.fantasticweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.multidex.MultiDexApplication;
import androidx.work.Configuration;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class WeatherApp extends MultiDexApplication implements Configuration.Provider {

    private static WeatherApp instance;
    private String lastCityName;

    public static WeatherApp getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new WeatherApp();
            return instance;
        }
    }

    public String getLastCityName() {return this.lastCityName;}

    public void setLastCityName(String lastCityName) {this.lastCityName = lastCityName;}

    @Inject
    HiltWorkerFactory workerFactory;

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build();
    }
}
