package com.avwaveaf.fleetifyreport.core.di;

import android.content.Context;

import androidx.room.Room;

import com.avwaveaf.fleetifyreport.core.data.db.FleetifyDatabase;
import com.avwaveaf.fleetifyreport.core.data.db.dao.VehicleDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public FleetifyDatabase provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, FleetifyDatabase.class, "fleetify_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public VehicleDao provideVehicleDao(FleetifyDatabase database) {
        return database.vehicleDao();
    }
}