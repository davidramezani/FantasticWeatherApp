package com.david.fantasticweatherapp.data.models.local.enums;

public enum WeatherType {
  CLOUDS("overcast clouds"),
  CLEAR("clear sky"),
  FEW_CLOUDS("few clouds"),
  SCATTERED_CLOUDS("scattered clouds"),
  BROKEN_CLOUDS("broken clouds"),
  SHOWER_RAIN("shower rain"),
  RAIN("rain"),
  LIGHT_RAIN("light rain"),
  THUNDERSTORM("thunderstorm"),
  SNOW("snow"),
  MIST("mist");

  private final String value;

  WeatherType(final String value) {
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
