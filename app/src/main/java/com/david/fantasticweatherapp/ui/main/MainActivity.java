package com.david.fantasticweatherapp.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.david.fantasticweatherapp.R;
import com.david.fantasticweatherapp.data.api.Resource;
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
        setClickListeners();
        checkUpdateWeatherData();
        checkUpdateWeatherData();
        getCurrentWeatherData();
    }

    private void getCurrentWeatherData() {
        mainViewModel.getCurrentWeatherData("Mashhad");
        mainViewModel.getWeatherData().observe(this, weatherData -> {
            if (weatherData.size() > 0) {
                mBinding.tvTemp.setText(String.valueOf((int) weatherData.get(0).main.temp));
                mBinding.tvWeatherStatus.setText(weatherData.get(0).weather.get(0).main);
            } else {
                //There is no data yet!!
            }
        });
    }

    private void checkUpdateWeatherData() {
        mainViewModel.updateCurrentWeatherData("Mashhad", Constants.WEATHER_API_ID);
        mainViewModel.checkUpdateWeatherData().observe(this, weatherResponseResource -> {
            if (weatherResponseResource instanceof Resource.Success) {
            } else if (weatherResponseResource instanceof Resource.Loading) {
            } else if (weatherResponseResource instanceof Resource.Error) {
            }
        });
    }

    private void setClickListeners() {
        mBinding.ivRefreshWeatherData.setOnClickListener(view -> {
            mainViewModel.updateCurrentWeatherData("Mashhad", Constants.WEATHER_API_ID);
        });
    }
}