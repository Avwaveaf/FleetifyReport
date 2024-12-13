package com.avwaveaf.fleetifyreport.core.data.data_source_impl.remote.report;

import com.avwaveaf.fleetifyreport.core.data.api_services.ReportApiService;
import com.avwaveaf.fleetifyreport.core.data.data_source.remote.report.ReportRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.data.entity.ReportDTO;
import com.avwaveaf.fleetifyreport.core.utils.NetworkErrorUtil;
import com.avwaveaf.fleetifyreport.core.utils.NetworkResponseUtil;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReportRemoteDataSourceImpl implements ReportRemoteDataSource {

    private final ReportApiService reportApiService;

    @Inject
    public ReportRemoteDataSourceImpl(ReportApiService reportApiService) {
        this.reportApiService = reportApiService;
    }


    @Override
    public Single<Resource<Object>> addReport(RequestBody vehicleId, RequestBody note, RequestBody userId, MultipartBody.Part photo) {
        return reportApiService.addReport(vehicleId, note, userId, photo)
                .map(NetworkResponseUtil::handleResponse)
                .onErrorReturn(NetworkErrorUtil::handleError);
    }

    @Override
    public Observable<Resource<List<ReportDTO>>> getAllReport(String userId, String vehicleLicenseNumber) {
        return reportApiService.getAllReports(userId, vehicleLicenseNumber)
                .map(NetworkResponseUtil::handleListResponse)
                .toObservable()
                .onErrorResumeNext(throwable -> Observable.just(NetworkErrorUtil.handleError(throwable)));
    }
}
