package com.avwaveaf.fleetifyreport.core.data.data_source.remote.report;

import com.avwaveaf.fleetifyreport.core.utils.Resource;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface ReportRemoteDataSource {
    Single<Resource<Object>> addReport(
            RequestBody vehicleId,
            RequestBody note,
            RequestBody userId,
            MultipartBody.Part photo
    );

    Single<Resource<Object>> getAllReport(
            String userId,
            String vehicleLicenseNumber
    );
}