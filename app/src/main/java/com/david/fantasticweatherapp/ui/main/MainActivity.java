package com.david.fantasticweatherapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.david.fantasticweatherapp.R;
import com.david.fantasticweatherapp.data.api.Resource;
import com.david.fantasticweatherapp.data.models.local.WeatherData;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.david.fantasticweatherapp.databinding.ActivityMainBinding;
import com.david.fantasticweatherapp.other.Constants;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        logWeatherData();
    }

    private void logWeatherData() {
        mainViewModel.getCurrentWeatherData("Mashhad", Constants.WEATHER_API_ID);
        mainViewModel.getWeatherData().observe(this, weatherResponseResource -> {
            if (weatherResponseResource instanceof Resource.Success) {
                WeatherResponse weatherData = ((Resource.Success<WeatherResponse>) weatherResponseResource).getData();
                mBinding.tvTemp.setText(String.valueOf((int) weatherData.main.temp));
                mBinding.tvWeatherStatus.setText(weatherData.weather.get(0).main);
            } else if (weatherResponseResource instanceof Resource.Loading) {
            } else if (weatherResponseResource instanceof Resource.Error) {
            }
        });
    }
}