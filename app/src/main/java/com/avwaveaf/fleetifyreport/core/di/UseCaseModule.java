package com.avwaveaf.fleetifyreport.core.di;

import com.avwaveaf.fleetifyreport.core.domain.repository.ProfileRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.profile.ProfileUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.profile.ProfileInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UseCaseModule {
    @Provides
    @Singleton
    public ProfileUseCase provideProfileUseCase(ProfileRepository profileRepository){
        return new ProfileInteractor(profileRepository);
    }
}