package com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor.report;

import com.avwaveaf.fleetifyreport.core.domain.entity.Report;
import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.DeleteAllReportUseCase;
import com.avwaveaf.fleetifyreport.core.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class DeleteAllReportInteractor implements DeleteAllReportUseCase {
    private final ReportRepository reportRepository;

    @Inject
    public DeleteAllReportInteractor(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @Override
    public Observable<Resource<List<Report>>> execute(String userId) {
        return Observable.fromCallable(() -> {
            reportRepository.deleteAllReports();
            return reportRepository.fetchReports(userId).blockingFirst();
        });
    }
}
