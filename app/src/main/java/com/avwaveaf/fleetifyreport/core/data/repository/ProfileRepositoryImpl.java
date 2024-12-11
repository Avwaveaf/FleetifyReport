package com.avwaveaf.fleetifyreport.core.data.repository;

import com.avwaveaf.fleetifyreport.core.data.data_source.local.profile.ProfileDataSource;
import com.avwaveaf.fleetifyreport.core.domain.entity.Profile;
import com.avwaveaf.fleetifyreport.core.domain.repository.ProfileRepository;

import javax.inject.Inject;


public class ProfileRepositoryImpl implements ProfileRepository {

    private final ProfileDataSource profileDataSource;

    @Inject
    public ProfileRepositoryImpl(ProfileDataSource profileDataSource) {
        this.profileDataSource = profileDataSource;
    }

    @Override
    public Profile getProfile() {
        return profileDataSource.getProfile();
    }
}
