package com.avwaveaf.fleetifyreport.core.data.mapper;

import com.avwaveaf.fleetifyreport.core.data.entity.ReportDTO;
import com.avwaveaf.fleetifyreport.core.domain.entity.Report;

import java.util.ArrayList;
import java.util.List;

public class ReportMapper {
    public static Report toDomain(ReportDTO reportDTO) {
        return new Report(
                reportDTO.getReportId(),
                reportDTO.getNote(),
                reportDTO.getCreatedAt(),
                reportDTO.getVehicleName(),
                reportDTO.getVehicleLicenseNumber(),
                reportDTO.getCreatedBy(),
                reportDTO.getPhoto(),
                reportDTO.getReportStatus(),
                reportDTO.getVehicleId()
        );
    }

    public static List<Report> toDomainList(List<ReportDTO> reportDTOS) {
        if (reportDTOS == null || reportDTOS.isEmpty()) {
            return new ArrayList<>();
        }
        List<Report> reports = new ArrayList<>();
        for (ReportDTO reportDTO : reportDTOS) {
            reports.add(toDomain(reportDTO));
        }
        return reports;
    }
}
