package com.avwaveaf.fleetifyreport.core.data.api_services;

import com.avwaveaf.fleetifyreport.core.data.entity.VehicleDTO;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface VehicleApiService {

    @GET("api/android/list_vehicle")
    Single<List<VehicleDTO>> getVehicleList();
}
