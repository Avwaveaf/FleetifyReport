package com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report;

import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

public interface GetAllReportUseCase {
    @NonNull Observable<Resource<List<Report>>> execute(String userId, String vehicleLicenseNumber);
}
