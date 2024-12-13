package com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report;

import com.avwaveaf.fleetifyreport.core.utils.Resource;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface AddReportUseCase {
    Single<Resource<Object>> execute(RequestBody vehicleId, RequestBody note, RequestBody userId, MultipartBody.Part photo);
}
