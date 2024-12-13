package com.avwaveaf.fleetifyreport.core.domain.repository;

import com.avwaveaf.fleetifyreport.core.utils.Resource;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface ReportRepository {
    Single<Resource<Object>> addReport(
            RequestBody vehicleId,
            RequestBody note,
            RequestBody userId,
            MultipartBody.Part body
    );

    Single<Resource<Object>> getAllReport(
            String userId,
            String vehicleLicenseNumber
    );
}
