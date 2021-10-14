package com.david.fantasticweatherapp.ui.main;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.david.fantasticweatherapp.data.api.Resource;
import com.david.fantasticweatherapp.data.models.db.tables.LocationData;
import com.david.fantasticweatherapp.data.models.local.enums.ErrorType;
import com.david.fantasticweatherapp.data.models.response.ErrorResponse;
import com.david.fantasticweatherapp.data.models.response.WeatherResponse;
import com.david.fantasticweatherapp.data.repository.WeatherRepository;
import com.david.fantasticweatherapp.ui.BaseViewModel;

import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import retrofit2.Response;

@HiltViewModel
public class MainViewModel extends BaseViewModel {

    private final CompositeDisposable compositeDisposable;
    private final WeatherRepository weatherRepository;
    public LocationData defaultLocation;

    private Subscription subscriptionWeatherData;

    //MutableData
    private final MutableLiveData<Resource<Boolean>> weatherDataUpdateStatus;
    private final MutableLiveData<List<WeatherResponse>> flowWeatherData;
    private final MutableLiveData<LocationData> liveLocationData;

    public MutableLiveData<LocationData> getLiveLocationData() {
        return liveLocationData;
    }

    public MutableLiveData<Resource<Boolean>> checkUpdateWeatherData() {
        return weatherDataUpdateStatus;
    }

    public MutableLiveData<List<WeatherResponse>> getWeatherData() {
        return flowWeatherData;
    }

    public void setDefaultLocation(LocationData locationData) {
        this.defaultLocation = locationData;
    }

    @Inject
    MainViewModel(Application app, WeatherRepository weatherRepository) {
        super(app);
        compositeDisposable = new CompositeDisposable();
        this.weatherRepository = weatherRepository;
        weatherDataUpdateStatus = new MutableLiveData<>();
        flowWeatherData = new MutableLiveData<>();
        liveLocationData = new MutableLiveData<>();
    }

    public void getLocation() {
        weatherRepository.getLocation().subscribeOn(Schedulers.io()).subscribe(new DisposableSubscriber<List<LocationData>>() {
            @Override
            public void onNext(List<LocationData> locationData) {
                if (locationData.size() > 0) {
                    setDefaultLocation(locationData.get(0));
                    liveLocationData.postValue(locationData.get(0));
                } else {
                    weatherRepository.setDefaultLocation();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getCurrentWeatherData(String cityName) {

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
                    flowWeatherData.postValue(weatherResponses);
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onComplete() {

                }
            });

        subscriptionWeatherData.request(Integer.MAX_VALUE);
    }

    public void updateCurrentWeatherData() {
        if (liveLocationData.getValue() != null) {
            weatherRepository.getCurrentWeatherData(liveLocationData.getValue().cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<WeatherResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        weatherDataUpdateStatus.postValue(new Resource.Loading<>());
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<WeatherResponse> weatherResponseResponse) {
                        weatherDataUpdateStatus.postValue(new Resource.Success<>(true));
                        if (weatherResponseResponse.isSuccessful()) {
                            WeatherResponse weatherResponse = weatherResponseResponse.body();
                            weatherResponse.locationName = defaultLocation.cityName;
                            weatherRepository.updateWeatherDataDb(weatherResponse);
                        } else {
                            ErrorResponse errorResponse = getErrorResponse(weatherResponseResponse);
                            weatherDataUpdateStatus.postValue(new Resource.Error<>(errorResponse.message, ErrorType.NOT_FOUND));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        weatherDataUpdateStatus.postValue(new Resource.Error<>(e.getMessage(), ErrorType.SERVER_ERROR));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
        }
    }

    public void updateLocation(LocationData locationData) {
        locationData.cityName = locationData.cityName.toLowerCase();
        weatherRepository.updateLocation(locationData);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
