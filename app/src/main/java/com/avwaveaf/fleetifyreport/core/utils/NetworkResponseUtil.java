package com.avwaveaf.fleetifyreport.core.utils;

import android.util.Log;
import retrofit2.Response;

/** @noinspection unchecked*/
public class NetworkResponseUtil {

    public static <T> Resource<T> handleResponse(Response<?> response) {
        if (response.isSuccessful()) {
            if (response.body() == null) {
                return (Resource<T>) Resource.success(new Object());
            } else {
                return (Resource<T>) Resource.success(response.body());
            }
        } else {
            int statusCode = response.code();
            String errorMessage;

            switch (statusCode) {
                case 400:
                    errorMessage = "Bad Request: " + response.message();
                    break;
                case 401:
                    errorMessage = "Unauthorized: " + response.message();
                    break;
                case 403:
                    errorMessage = "Forbidden: " + response.message();
                    break;
                case 404:
                    errorMessage = "Not Found: " + response.message();
                    break;
                case 500:
                    errorMessage = "Server Error: " + response.message();
                    break;
                default:
                    errorMessage = "Unexpected Error: " + response.message();
            }

            // Log the error for debugging
            Log.e("NetworkResponse", "Error: " + errorMessage);

            return Resource.error(errorMessage, null);
        }
    }
}