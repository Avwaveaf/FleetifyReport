package com.avwaveaf.fleetifyreport.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avwaveaf.fleetifyreport.core.domain.entity.Profile;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.profile.ProfileUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final MutableLiveData<Profile> profile = new MutableLiveData<>();
    private final ProfileUseCase profileUseCase;

    @Inject
    public HomeViewModel(ProfileUseCase profileUseCase) {
        this.profileUseCase = profileUseCase;
        loadProfile();
    }

    public LiveData<Profile> getProfileLiveData() {
        return profile;
    }

    public void loadProfile() {
        Profile fetchedProfile = profileUseCase.getProfile();
        profile.setValue(fetchedProfile);
    }
}
