package com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.profile;

import com.avwaveaf.fleetifyreport.core.domain.entity.Profile;
import com.avwaveaf.fleetifyreport.core.domain.repository.ProfileRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.profile.ProfileUseCase;

import javax.inject.Inject;

public class ProfileInteractor implements ProfileUseCase {

    private final ProfileRepository profileRepository;

    @Inject
    public ProfileInteractor(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile getProfile() {
        return profileRepository.getProfile();
    }
}
