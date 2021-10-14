package com.david.fantasticweatherapp.ui;

import android.app.Application;

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
        } catch (Exception e){
            return new ErrorResponse("500","unknown error");
        }
    }

}
