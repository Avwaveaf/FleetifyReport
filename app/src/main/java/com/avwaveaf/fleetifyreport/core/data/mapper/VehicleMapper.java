package com.avwaveaf.fleetifyreport.core.data.mapper;

import com.avwaveaf.fleetifyreport.core.data.entity.VehicleDTO;
import com.avwaveaf.fleetifyreport.core.domain.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleMapper {
    public static Vehicle toDomain(VehicleDTO vehicleDTO) {
        return new Vehicle(vehicleDTO.getVehicleId(), vehicleDTO.getLicenseNumber(), vehicleDTO.getType());
    }

    public static List<Vehicle> toDomainList(List<VehicleDTO> vehicleDTOList) {
        if (vehicleDTOList == null || vehicleDTOList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Vehicle> vehicles = new ArrayList<>();
        for (VehicleDTO vehicleDTO : vehicleDTOList) {
            vehicles.add(toDomain(vehicleDTO));
        }
        return vehicles;
    }
}
