package com.david.fantasticweatherapp.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.core.content.ContextCompat;

import com.david.fantasticweatherapp.R;
import com.david.fantasticweatherapp.data.models.local.enums.WeatherType;

public final class SetWeatherStatusIcon {
    private SetWeatherStatusIcon(){}

    public static void setImageIcon(ImageView imageView, Context context, String weatherStatus) {
        WeatherType weatherType = WeatherType.fromString(weatherStatus);

        if (weatherType != null) {
            switch (weatherType) {
                case CLEAR:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.clearsky));
                    break;
                case FEW_CLOUDS:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.fewclouds));
                    break;
                case SCATTERED_CLOUDS:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.scatteredclouds));
                    break;
                case BROKEN_CLOUDS:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.brokenclouds));
                    break;
                case SHOWER_RAIN:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.showerrain));
                    break;
                case RAIN:
                case LIGHT_RAIN:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rain));
                    break;
                case THUNDERSTORM:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.thunderstorm));
                    break;
                case SNOW:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.snow));
                    break;
                case MIST:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.mist));
                case CLOUDS:
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.overcastclouds));
                    break;
            }
        }
    }

    public static void setRemoteImageIcon(RemoteViews views, Context context, String weatherStatus) {
        WeatherType weatherType = WeatherType.fromString(weatherStatus);

        if (weatherType != null) {
            switch (weatherType) {
                case CLEAR:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.clearsky);
                    break;
                case FEW_CLOUDS:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.fewclouds);
                    break;
                case SCATTERED_CLOUDS:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.scatteredclouds);
                    break;
                case BROKEN_CLOUDS:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.brokenclouds);
                    break;
                case SHOWER_RAIN:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.showerrain);
                    break;
                case RAIN:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.rain);
                    break;
                case THUNDERSTORM:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.thunderstorm);
                    break;
                case SNOW:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.snow);
                    break;
                case MIST:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.mist);
                case CLOUDS:
                    views.setImageViewResource(R.id.ivWidgetWeatherStatus, R.drawable.overcastclouds);
                    break;
            }
        }
    }
}
