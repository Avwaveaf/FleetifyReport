package com.avwaveaf.fleetifyreport.core.domain.repository;

import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
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

    Observable<Resource<List<Report>>> getAllReport(
            String userId,
            String vehicleLicenseNumber
    );
}
