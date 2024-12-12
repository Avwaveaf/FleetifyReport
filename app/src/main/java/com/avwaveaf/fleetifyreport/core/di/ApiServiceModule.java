package com.avwaveaf.fleetifyreport.core.di;

import com.avwaveaf.fleetifyreport.core.data.api_services.VehicleApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class ApiServiceModule {

    @Provides
    @Singleton
    public VehicleApiService provideVehicleApiService(Retrofit retrofit) {
        return retrofit.create(VehicleApiService.class);
    }
}
