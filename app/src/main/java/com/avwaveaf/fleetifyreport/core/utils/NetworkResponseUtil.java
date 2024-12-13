package com.avwaveaf.fleetifyreport.core.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * @noinspection unchecked
 */
public class NetworkResponseUtil {

    // Handle generic response
    public static <T> Resource<T> handleResponse(Response<?> response) {
        if (response.isSuccessful()) {
            if (response.body() == null) {
                return (Resource<T>) Resource.success(new Object());
            } else {
                return (Resource<T>) Resource.success(response.body());
            }
        } else {
            return handleError(response);
        }
    }

    // Handle response specifically for List<T>
    public static <T> Resource<List<T>> handleListResponse(Response<List<T>> response) {
        if (response.isSuccessful()) {
            if (response.body() == null) {
                return Resource.success(new ArrayList<>());
            } else {
                return Resource.success(response.body());
            }
        } else if (response.code() == 404) {
            return Resource.success(new ArrayList<>());
        } else {
            return handleError(response);
        }
    }

    // Handle error response
    private static Resource handleError(Response response) {
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

        return Resource.error(errorMessage, null);
    }
}
