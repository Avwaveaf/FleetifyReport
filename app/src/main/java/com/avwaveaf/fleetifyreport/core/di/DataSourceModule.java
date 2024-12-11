package com.avwaveaf.fleetifyreport.core.di;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.profile.ProfileDataSource;
import com.avwaveaf.fleetifyreport.core.data.data_source_impl.local.profile.ProfileDataSourceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DataSourceModule {
    @Provides
    @Singleton
    public ProfileDataSource provideProfileDataSource(){
        return new ProfileDataSourceImpl();
    }
}
