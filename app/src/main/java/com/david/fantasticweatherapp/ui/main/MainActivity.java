package com.david.fantasticweatherapp.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.david.fantasticweatherapp.R;
import com.david.fantasticweatherapp.data.api.Resource;
import com.david.fantasticweatherapp.data.models.local.enums.ErrorType;
import com.david.fantasticweatherapp.databinding.ActivityMainBinding;
import com.david.fantasticweatherapp.ui.locationdialog.LocationBottomDialog;
import com.david.fantasticweatherapp.utils.SetWeatherStatusIcon;

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
        getLocation();
        observeUpdateWeatherData();
        observeCurrentWeatherData();
    }

    private void getLocation() {
        mainViewModel.getLocation();
        mainViewModel.getLiveLocationData().observe(this, locationData -> {
            updateCurrentWeatherData();
            mainViewModel.getCurrentWeatherData(locationData.cityName);
        });
    }

    private void observeCurrentWeatherData() {
        mainViewModel.getWeatherData().observe(this, weatherData -> {
            if (weatherData.size() > 0) {
                mBinding.tvLocation.setText(weatherData.get(0).name);
                mBinding.tvTemp.setText(String.valueOf((int) weatherData.get(0).main.temp));
                mBinding.tvWeatherStatus.setText(weatherData.get(0).weather.get(0).main);
                mBinding.txtHumidityValue.setText(String.valueOf(weatherData.get(0).main.humidity));
                SetWeatherStatusIcon.setImageIcon(mBinding.ivWeatherStatus, this, weatherData.get(0).weather.get(0).description);
            } else {
                //There is no data yet!!
            }
        });
    }

    private void observeUpdateWeatherData() {
        mainViewModel.checkUpdateWeatherData().observe(this, weatherResponseResource -> {
            if (weatherResponseResource instanceof Resource.Success) {
                mBinding.setLoadingData(false);
                mBinding.setWrongLocation(false);
                mBinding.setInternetProblem(false);
            } else if (weatherResponseResource instanceof Resource.Loading) {
                mBinding.setLoadingData(true);
                mBinding.setWrongLocation(false);
                mBinding.setInternetProblem(false);
            } else if (weatherResponseResource instanceof Resource.Error) {
                if (((Resource.Error<Boolean>) weatherResponseResource).getErrorType() == ErrorType.NOT_FOUND) {
                    mBinding.setWrongLocation(true);
                    mBinding.setInternetProblem(false);
                    mBinding.tvError.setText(((Resource.Error<Boolean>) weatherResponseResource).getMessage());
                } else if (((Resource.Error<Boolean>) weatherResponseResource).getErrorType() == ErrorType.NETWORK_ERROR) {
                    mBinding.setInternetProblem(true);
                    mBinding.setWrongLocation(false);
                    mBinding.tvError.setText(((Resource.Error<Boolean>) weatherResponseResource).getMessage());
                }
                mBinding.setLoadingData(false);
            }
        });
    }

    private void setClickListeners() {
        mBinding.ivRefreshWeatherData.setOnClickListener(view -> {
            mainViewModel.getLocation();
        });
        mBinding.llLocationCnt.setOnClickListener(view -> {
            showLocationForm();
        });
        mBinding.llErrorStatusCnt.setOnClickListener(view->{
            showLocationForm();
        });
        mBinding.llInternetProblemCnt.setOnClickListener(view->{
            mainViewModel.getLocation();
        });
    }

    private void showLocationForm() {
        new LocationBottomDialog().show(getSupportFragmentManager(), "LocationBottomDialog");
    }

    private void updateCurrentWeatherData() {
        mainViewModel.updateCurrentWeatherData();
    }
}