package com.david.fantasticweatherapp.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.david.fantasticweatherapp.data.models.db.WeatherDB;
import com.david.fantasticweatherapp.other.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public final class WeatherDBModule {

    @Provides
    @Singleton
    RoomDatabase.Callback provideRoomDataBaseCallBack() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }

            @Override
            public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                super.onDestructiveMigration(db);
            }
        };
    }

    @Provides
    @Singleton
    WeatherDB provideWeatherDb(
        Application app,
        RoomDatabase.Callback callback
    ) {
        return Room.databaseBuilder(app, WeatherDB.class, Constants.WeatherDBNAME)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build();
    }

}
