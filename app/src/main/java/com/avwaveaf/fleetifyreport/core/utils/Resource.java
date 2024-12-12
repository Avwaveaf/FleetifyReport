package com.avwaveaf.fleetifyreport.core.utils;


import lombok.Getter;

@Getter
public class Resource<T> {
    public enum Status { SUCCESS, ERROR, LOADING }

    private final T data;
    private final String message;
    private final Status status;

    private Resource(T data, String message, Status status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(data, null, Status.SUCCESS);
    }

    public static <T> Resource<T> error(String message, T data) {
        return new Resource<>(data, message, Status.ERROR);
    }

    public static <T> Resource<T> loading(T data) {
        return new Resource<>(data, null, Status.LOADING);
    }

}
