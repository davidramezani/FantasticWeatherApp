package com.david.fantasticweatherapp;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class WeatherApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
