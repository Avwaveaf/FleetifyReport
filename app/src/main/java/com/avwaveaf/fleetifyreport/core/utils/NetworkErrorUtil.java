    package com.avwaveaf.fleetifyreport.core.utils;

    import java.net.UnknownHostException;

    public class NetworkErrorUtil {
        public static <T> Resource<T> handleError(Throwable throwable) {
            if (throwable instanceof UnknownHostException) {
                return Resource.error("Kesalahan jaringan. Periksa koneksi internet Anda.", null);
            } else if (throwable instanceof java.net.SocketTimeoutException) {
                return Resource.error("Waktu habis. Silakan coba lagi.", null);
            } else if (throwable instanceof java.io.IOException) {
                return Resource.error("Kesalahan komunikasi. Coba lagi nanti.", null);
            } else {
                return Resource.error("Kesalahan tidak diketahui. Hubungi tim dukungan.", null);
            }
        }
    }
