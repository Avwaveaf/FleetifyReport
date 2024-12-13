package com.avwaveaf.fleetifyreport.core.data.api_services;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ReportApiService {


    @GET("api/android/read_all_laporan")
    Single<Response<Object>> getAllReports(
            @Query("userId") String userId,
            @Query("vehicleLicenseNumber") String vehicleLicenseNumber
    );

    @Multipart
    @POST("api/android/add_laporan")
    Single<Response<Void>> addReport(
            @Part("vehicleId") RequestBody vehicleId,
            @Part("note") RequestBody note,
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part photo
    );
}
