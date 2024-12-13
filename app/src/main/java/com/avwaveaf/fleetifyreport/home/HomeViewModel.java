package com.avwaveaf.fleetifyreport.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avwaveaf.fleetifyreport.core.domain.entity.Profile;
import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.profile.ProfileUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.DeleteAllReportUseCase;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.GetAllReportUseCase;
import com.avwaveaf.fleetifyreport.core.utils.NetworkErrorUtil;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private final GetAllReportUseCase getAllReportUseCase;
    private final DeleteAllReportUseCase deleteAllReportUseCase;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Resource<List<Report>>> _reportState = new MutableLiveData<>();
    private final MutableLiveData<Profile> profile = new MutableLiveData<>();
    private final ProfileUseCase profileUseCase;
    public LiveData<Resource<List<Report>>> reportState = _reportState;

    @Inject
    public HomeViewModel(GetAllReportUseCase getAllReportUseCase, DeleteAllReportUseCase deleteAllReportUseCase, ProfileUseCase profileUseCase) {
        this.getAllReportUseCase = getAllReportUseCase;
        this.deleteAllReportUseCase = deleteAllReportUseCase;
        this.profileUseCase = profileUseCase;
        loadProfile();
        loadAllReports();
    }

    public LiveData<Profile> getProfileLiveData() {
        return profile;
    }

    public void loadProfile() {
        Profile fetchedProfile = profileUseCase.getProfile();
        profile.setValue(fetchedProfile);
    }

    public void loadAllReports() {
        _reportState.postValue(Resource.loading(null));

        // Pass vehicleNumber as a parameter to the use case
        disposable.add(getAllReportUseCase.execute("k7jPfBcEjFnSlG2", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _reportState::postValue,
                        throwable -> _reportState.postValue(NetworkErrorUtil.handleError(throwable))
                ));
    }


    public void loadAllReports(String vehicleNumber) {
        _reportState.postValue(Resource.loading(null));

        // Pass vehicleNumber as a parameter to the use case
        disposable.add(getAllReportUseCase.execute("k7jPfBcEjFnSlG2", vehicleNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _reportState::postValue,
                        throwable -> _reportState.postValue(NetworkErrorUtil.handleError(throwable))
                ))
        ;
    }

    public void refreshReports() {
        _reportState.postValue(Resource.loading(null));
        disposable.add(deleteAllReportUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::loadAllReports
                ));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
