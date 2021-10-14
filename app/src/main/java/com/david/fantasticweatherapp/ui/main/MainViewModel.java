package com.david.fantasticweatherapp.ui.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.david.fantasticweatherapp.data.api.Resource;
import com.david.fantasticweatherapp.data.models.local.enums.ErrorType;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.david.fantasticweatherapp.data.repository.WeatherRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import retrofit2.Response;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable;
    private final WeatherRepository weatherRepository;

    //MutableData
    private final MutableLiveData<Resource<WeatherResponse>> weatherData;
    private final MutableLiveData<List<WeatherResponse>> flowWeatherData;

    public MutableLiveData<Resource<WeatherResponse>> checkUpdateWeatherData() {
        return weatherData;
    }

    public MutableLiveData<List<WeatherResponse>> getWeatherData() {
        return flowWeatherData;
    }

    @Inject
    MainViewModel(SavedStateHandle handle, WeatherRepository weatherRepository) {
        compositeDisposable = new CompositeDisposable();
        this.weatherRepository = weatherRepository;
        weatherData = new MutableLiveData<>();
        flowWeatherData = new MutableLiveData<>();
    }

    public void updateCurrentWeatherData(String query, String appId) {
        weatherRepository.getCurrentWeatherData(query, appId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Response<WeatherResponse>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onNext(@NonNull Response<WeatherResponse> weatherResponseResponse) {
                    if (weatherResponseResponse.isSuccessful()) {
                        weatherRepository.updateWeatherDataDb(weatherResponseResponse.body());
                    }
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    weatherData.postValue(new Resource.Error<>(e.getMessage(), ErrorType.SERVER_ERROR));
                }

                @Override
                public void onComplete() {

                }
            });
    }

    public void getCurrentWeatherData(String cityName) {
        weatherRepository.getWeatherData(cityName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new DisposableSubscriber<List<WeatherResponse>>() {
                @Override
                public void onNext(List<WeatherResponse> weatherResponses) {
                    flowWeatherData.postValue(weatherResponses);
                }

                @Override
                public void onError(Throwable t) {
                    Log.i("getWeather", t.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
