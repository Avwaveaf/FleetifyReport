package com.avwaveaf.fleetifyreport.core.domain.use_cases.interactor;

import com.avwaveaf.fleetifyreport.core.domain.repository.ReportRepository;
import com.avwaveaf.fleetifyreport.core.domain.use_cases.contract.report.DeleteAllReportUseCase;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;

public class DeleteAllReportInteractor implements DeleteAllReportUseCase {
    private final ReportRepository reportRepository;

    @Inject
    public DeleteAllReportInteractor(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @Override
    public Completable execute() {
        // Delete all reports from local database
        return Completable.fromAction(reportRepository::deleteAllReports);
    }
}
