package com.avwaveaf.fleetifyreport.new_report;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.AddReportUseCase;
import com.avwaveaf.fleetifyreport.core.utils.FileUtil;
import com.avwaveaf.fleetifyreport.core.utils.NetworkErrorUtil;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AddReportViewModel extends ViewModel {
    private final AddReportUseCase useCase;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Resource<Object>> _addReportResult = new MutableLiveData<>();
    public LiveData<Resource<Object>> addReportResult = _addReportResult;

    @Inject
    public AddReportViewModel(AddReportUseCase useCase) {
        this.useCase = useCase;
    }

    public void addReport(String vehicleId, String note, String userId, File photo) {
        _addReportResult.postValue(Resource.loading(null));  // Immediately set loading state

        disposable.add(useCase.execute(FileUtil.textToBody(vehicleId), FileUtil.textToBody(note), FileUtil.textToBody(userId), FileUtil.fileToBody(photo))
                .subscribeOn(Schedulers.io())  // Background thread for network request
                .observeOn(AndroidSchedulers.mainThread())  // Observe results on the main thread
                .subscribe(
                        _addReportResult::postValue,  // On success, post the result
                        throwable -> _addReportResult.postValue(NetworkErrorUtil.handleError(throwable))  // On error, post the error
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
