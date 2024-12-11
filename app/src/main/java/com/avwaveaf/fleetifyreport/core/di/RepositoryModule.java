package com.avwaveaf.fleetifyreport.core.di;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.profile.ProfileDataSource;
import com.avwaveaf.fleetifyreport.core.data.repository.ProfileRepositoryImpl;
import com.avwaveaf.fleetifyreport.core.domain.repository.ProfileRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public ProfileRepository provideProfileRepository(ProfileDataSource profileDataSource){
        return new ProfileRepositoryImpl(profileDataSource);
    }
}
