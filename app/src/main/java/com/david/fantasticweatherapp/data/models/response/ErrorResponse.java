package com.david.fantasticweatherapp.data.models.response;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("cod")
    public String cod;
    @SerializedName("message")
    public String message;


    public ErrorResponse(String cod, String message) {
        this.cod = cod;
        this.message = message;
    }
}
