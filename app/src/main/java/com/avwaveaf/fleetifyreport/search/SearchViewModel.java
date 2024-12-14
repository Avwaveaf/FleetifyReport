package com.avwaveaf.fleetifyreport.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
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
public class SearchViewModel extends ViewModel {
    private final GetAllReportUseCase getAllReportUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();

    private final MutableLiveData<Resource<List<Report>>> _reportState = new MutableLiveData<>();
    public LiveData<Resource<List<Report>>> reportState = _reportState;

    @Inject
    public SearchViewModel(GetAllReportUseCase getAllReportUseCase) {
        this.getAllReportUseCase = getAllReportUseCase;
        loadAllReports("");
    }

    public void loadAllReports(String query) {

        _reportState.postValue(Resource.loading(null));

        disposable.add(getAllReportUseCase.execute("k7jPfBcEjFnSlG2", query) // Use the actual search query
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        _reportState::postValue,
                        throwable -> _reportState.postValue(NetworkErrorUtil.handleError(throwable))
                ));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
