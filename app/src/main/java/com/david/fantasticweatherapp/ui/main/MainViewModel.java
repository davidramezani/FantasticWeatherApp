package com.david.fantasticweatherapp.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.david.fantasticweatherapp.data.api.Resource;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.david.fantasticweatherapp.data.repository.WeatherRepository;
import com.david.fantasticweatherapp.data.models.local.enums.ErrorType;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private String helloString = "Welcome Back To Java";
    private WeatherRepository weatherRepository;

    //MutableData
    private MutableLiveData<Resource<WeatherResponse>> weatherData;

    public MutableLiveData<Resource<WeatherResponse>> getWeatherData() {
        return weatherData;
    }

    @Inject
    MainViewModel(SavedStateHandle handle, WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        weatherData = new MutableLiveData();
    }

    public String getHelloString() {
        return helloString;
    }

    public void getCurrentWeatherData(String query, String appId){
        weatherRepository.getCurrentWeatherData(query,appId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<WeatherResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Response<WeatherResponse> weatherResponseResponse) {
                        if(weatherResponseResponse.isSuccessful()){
                            WeatherResponse weatherResponse = weatherResponseResponse.body();
                            weatherData.postValue(new Resource.Success<WeatherResponse>(weatherResponse));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        weatherData.postValue(new Resource.Error<WeatherResponse>(e.getMessage(), ErrorType.SERVER_ERROR));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
