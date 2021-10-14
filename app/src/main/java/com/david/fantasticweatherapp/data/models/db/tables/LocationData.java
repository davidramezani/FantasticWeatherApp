package com.david.fantasticweatherapp.data.models.db.tables;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Location")
public class LocationData {

    @PrimaryKey(autoGenerate = false)
    public int id;
    public String cityName;
    public Double lat;
    public Double lon;

    public LocationData(int id, String cityName, Double lat, Double lon) {
        this.id = id;
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
    }
}
