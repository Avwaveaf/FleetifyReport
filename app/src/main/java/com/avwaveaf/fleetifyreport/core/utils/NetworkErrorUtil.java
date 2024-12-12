package com.avwaveaf.fleetifyreport.core.utils;

import java.net.UnknownHostException;

public class NetworkErrorUtil {

    public static <T> Resource<T> handleError(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            return Resource.error("Network Error: No Internet Connection", null);
        } else if (throwable instanceof java.net.SocketTimeoutException) {
            return Resource.error("Network Error: Timeout Occurred", null);
        } else if (throwable instanceof java.io.IOException) {
            return Resource.error("Network Error: IOException", null);
        } else {
            return Resource.error("Unknown Error: Something went wrong", null);
        }
    }
}
