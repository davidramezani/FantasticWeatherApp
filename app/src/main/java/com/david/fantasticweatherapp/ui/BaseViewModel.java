package com.david.fantasticweatherapp.ui;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;

import androidx.lifecycle.AndroidViewModel;

import com.david.fantasticweatherapp.data.models.response.ErrorResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class BaseViewModel extends AndroidViewModel {

    @Inject
    public BaseViewModel(Application app) {
        super(app);
    }

    public <T> ErrorResponse getErrorResponse(Response<T> response) {
        try {
            return new Gson().fromJson(response.errorBody().string(), ErrorResponse.class);
        } catch (Exception e) {
            return new ErrorResponse("500", "unknown error");
        }
    }

    public Boolean hasInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getSystemService(
            Context.CONNECTIVITY_SERVICE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager.getActiveNetwork() == null) return false;
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (connectivityManager.getNetworkCapabilities(activeNetwork) == null) return false;
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return true;
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return true;
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return true;
            return false;
        } else {
            if(connectivityManager.getActiveNetworkInfo() != null){
               switch (connectivityManager.getActiveNetworkInfo().getType()) {
                   case ConnectivityManager.TYPE_WIFI:
                   case ConnectivityManager.TYPE_ETHERNET:
                   case ConnectivityManager.TYPE_MOBILE:
                       return true;
               }
               return false;
            }
            return false;
        }
    }

}
