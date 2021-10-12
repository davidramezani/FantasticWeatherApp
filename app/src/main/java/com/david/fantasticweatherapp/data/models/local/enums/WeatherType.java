package com.david.fantasticweatherapp.data.models.local.enums;

import java.util.Arrays;

public enum WeatherType {
    CLEAR("clear sky"),
    FEW_CLOUDS("few clouds"),
    SCATTERED_CLOUDS("scattered clouds"),
    BROKEN_CLOUDS("broken clouds"),
    SHOWER_RAIN("shower rain"),
    RAIN("rain"),
    THUNDERSTORM("thunderstorm"),
    SNOW("snow"),
    MIST("mist");

    private String value;

    WeatherType(final String value){
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static WeatherType fromString(String text) {
        for (WeatherType b : WeatherType.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
