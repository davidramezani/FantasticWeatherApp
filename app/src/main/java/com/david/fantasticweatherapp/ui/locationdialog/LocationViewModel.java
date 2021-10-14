package com.david.fantasticweatherapp.ui.locationdialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.david.fantasticweatherapp.data.models.db.tables.LocationData;
import com.david.fantasticweatherapp.data.repository.WeatherRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;

@HiltViewModel
public class LocationViewModel extends ViewModel {

    private final WeatherRepository weatherRepository;
    private final MutableLiveData<LocationData> liveLocationData;
    public LocationData defaultLocation;

    public MutableLiveData<LocationData> getLiveLocationData() {
        return liveLocationData;
    }

    @Inject
    LocationViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        liveLocationData = new MutableLiveData<>();
    }

    public void setDefaultLocation(LocationData locationData) {
        this.defaultLocation = locationData;
    }

    public void updateLocation(LocationData locationData) {
        locationData.cityName = locationData.cityName.toLowerCase();
        weatherRepository.updateLocation(locationData);
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

}
