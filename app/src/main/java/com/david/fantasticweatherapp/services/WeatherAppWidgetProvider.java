package com.david.fantasticweatherapp.services;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.david.fantasticweatherapp.R;
import com.david.fantasticweatherapp.WeatherApp;
import com.david.fantasticweatherapp.data.models.db.tables.LocationData;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.david.fantasticweatherapp.data.repository.WeatherRepository;
import com.david.fantasticweatherapp.utils.SetWeatherStatusIcon;
import com.google.gson.Gson;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class WeatherAppWidgetProvider extends AppWidgetProvider {

    private WorkManager workManager;
    private Subscription subscriptionLocationData;
    private Subscription subscriptionWeatherData;
    private Context context;

    @Inject
    WeatherRepository weatherRepository;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;
        checkLocation();
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        this.context = context;
        checkLocation();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        this.context = context;
        Bundle extra = intent.getExtras();
        if(extra != null){
            String weatherData = extra.getString("WeatherData");
            if (weatherData != null && !weatherData.equals("null")) {
                WeatherResponse weatherResponse = new Gson().fromJson(weatherData, WeatherResponse.class);
                weatherRepository.updateWeatherDataDb(weatherResponse);
                updateWidget(weatherResponse.name);
            } else {
                String cityName = extra.getString("CityName");
                updateWidget(cityName);
            }
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        workManager = WorkManager.getInstance(context);
        workManager.cancelAllWork();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        if (subscriptionLocationData != null) {
            subscriptionLocationData.cancel();
        }
        if (subscriptionWeatherData != null) {
            subscriptionWeatherData.cancel();
        }
    }

    private void checkLocation() {
        if (subscriptionLocationData != null) {
            subscriptionLocationData.cancel();
        }

        weatherRepository.getLocation().subscribeOn(Schedulers.io()).subscribe(new FlowableSubscriber<List<LocationData>>() {
            @Override
            public void onSubscribe(@NonNull Subscription s) {
                subscriptionLocationData = s;
            }

            @Override
            public void onNext(List<LocationData> locationData) {
                if (locationData.size() > 0) {
                    WeatherApp weatherApp = WeatherApp.getInstance();
                    String cityName = weatherApp.getLastCityName();
                    if (cityName == null || !cityName.equals(locationData.get(0).cityName)) {
                        weatherApp.setLastCityName(locationData.get(0).cityName);
                        resetWorkManager(locationData.get(0).cityName);
                    }
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        subscriptionLocationData.request(1);
    }

    private void resetWorkManager(String cityName) {

        if (workManager != null) {
            workManager.cancelAllWork();
        }

        Data.Builder data = new Data.Builder();
        data.putString("CityName", cityName);

        Constraints constraints = new Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build();

        workManager = WorkManager.getInstance(context);

        PeriodicWorkRequest task = new PeriodicWorkRequest.Builder(UpdateWeatherWorkManager.class, 30, TimeUnit.MINUTES)
            .setInputData(data.build())
            .setConstraints(constraints)
            .build();

        workManager.enqueueUniquePeriodicWork("UpdateWeatherWorker", ExistingPeriodicWorkPolicy.REPLACE, task);
    }

    private void updateWidget(String cityName) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(new ComponentName(context, WeatherAppWidgetProvider.class));
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget_layout);
            setWeatherData(cityName, views, appWidgetManager, appWidgetId);
        }
    }

    private void setWeatherData(String cityName, RemoteViews views, AppWidgetManager appWidgetManager, int appWidgetId) {
        if (subscriptionWeatherData != null) {
            subscriptionWeatherData.cancel();
        }

        weatherRepository.getWeatherData(cityName).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new FlowableSubscriber<List<WeatherResponse>>() {
                @Override
                public void onSubscribe(@NonNull Subscription s) {
                    subscriptionWeatherData = s;
                }

                @Override
                public void onNext(List<WeatherResponse> weatherResponses) {
                    if (weatherResponses.size() > 0) {
                        views.setTextViewText(R.id.tvTemp, String.valueOf((int) weatherResponses.get(0).main.temp));
                        views.setTextViewText(R.id.tvWidgetHumidity, String.valueOf(weatherResponses.get(0).main.humidity));
                        views.setTextViewText(R.id.tvLocation, String.valueOf(weatherResponses.get(0).name));
                        views.setTextViewText(R.id.tvWidgetWeatherStatus, String.valueOf(weatherResponses.get(0).weather.get(0).main));
                        views.setTextViewText(R.id.tvDescription, String.valueOf(weatherResponses.get(0).weather.get(0).description));
                        SetWeatherStatusIcon.setRemoteImageIcon(views, context, weatherResponses.get(0).weather.get(0).main);
                        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.ivWidgetWeatherStatus);
                    }
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onComplete() {

                }
            });

        subscriptionWeatherData.request(1);
    }

}
