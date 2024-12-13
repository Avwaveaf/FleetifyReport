package com.avwaveaf.fleetifyreport.core.data.data_source.remote.report;

import com.avwaveaf.fleetifyreport.core.data.entity.ReportDTO;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
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

    @NonNull Observable<Resource<List<ReportDTO>>> getAllReport(
            String userId,
            String vehicleLicenseNumber
    );
}
