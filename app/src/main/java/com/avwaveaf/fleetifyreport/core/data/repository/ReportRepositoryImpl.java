package com.avwaveaf.fleetifyreport.core.data.repository;

import com.avwaveaf.fleetifyreport.core.data.data_source.remote.report.ReportRemoteDataSource;
import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReportRepositoryImpl implements ReportRepository {
    private final ReportRemoteDataSource reportRemoteDataSource;

    @Inject
    public ReportRepositoryImpl(ReportRemoteDataSource reportRemoteDataSource) {
        this.reportRemoteDataSource = reportRemoteDataSource;
    }

    @Override
    public Single<Resource<Object>> addReport(RequestBody vehicleId, RequestBody note, RequestBody userId, MultipartBody.Part body) {
        return reportRemoteDataSource.addReport(vehicleId, note, userId, body);
    }

    @Override
    public Single<Resource<Object>> getAllReport(String userId, String vehicleLicenseNumber) {
        return reportRemoteDataSource.getAllReport(userId, vehicleLicenseNumber);
    }
}
