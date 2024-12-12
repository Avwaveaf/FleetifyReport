package com.avwaveaf.fleetifyreport.new_report;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.vehicle.GetVehicleUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class VehicleViewModel extends ViewModel {
    private final GetVehicleUseCase getVehiclesUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final MutableLiveData<List<Vehicle>> _vehicleList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> _errorMessage = new MutableLiveData<>(null);
    public LiveData<List<Vehicle>> vehicleList = _vehicleList;
    public LiveData<Boolean> isLoading = _isLoading;
    public LiveData<String> errorMessage = _errorMessage;

    @Inject
    public VehicleViewModel(GetVehicleUseCase getVehiclesUseCase) {
        this.getVehiclesUseCase = getVehiclesUseCase;
    }

    @SuppressLint("CheckResult")
    public void fetchVehicles() {
        compositeDisposable.add(
                getVehiclesUseCase.execute()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                resource -> {
                                    switch (resource.getStatus()) {
                                        case LOADING:
                                            _isLoading.setValue(true);
                                            break;
                                        case SUCCESS:
                                            _isLoading.setValue(false);
                                            if (resource.getData() != null && !resource.getData().isEmpty()) {
                                                _vehicleList.setValue(resource.getData());
                                            } else {
                                                _errorMessage.setValue("No vehicles found");
                                            }
                                            break;
                                        case ERROR:
                                            _isLoading.setValue(false);
                                            _errorMessage.setValue(resource.getMessage());
                                            break;
                                    }
                                },
                                throwable -> {
                                    _isLoading.setValue(false);
                                    _errorMessage.setValue(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
