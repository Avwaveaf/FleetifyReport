package com.avwaveaf.fleetifyreport.core.utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    public Observable<Resource<ResultType>> asObservable() {
        return Observable.defer(() ->
                loadFromCache()
                        .flatMap(cachedData -> shouldFetch(cachedData)
                                .flatMapObservable(needsFetch -> {
                                    if (needsFetch) {
                                        return createCall()
                                                .flatMap(response -> {
                                                    // Process and save the response
                                                    ResultType processedResult = processResponse(response);
                                                    saveCallResult(processedResult);
                                                    return Observable.just(Resource.success(processedResult));
                                                })
                                                .onErrorReturn(this::handleError);
                                    } else {
                                        // Use cached data if no fetch is needed
                                        return Observable.just(Resource.success(cachedData));
                                    }
                                }))
                        .onErrorReturn(this::handleError)
        );
    }

    // Create the network call
    protected abstract Observable<RequestType> createCall();

    // Process the network response
    protected abstract ResultType processResponse(RequestType response);

    // Save the processed result to local storage
    protected abstract void saveCallResult(ResultType item);

    // Determine if a fetch is needed
    // Default implementation checks if cached data is empty or null
    protected Single<Boolean> shouldFetch(ResultType cachedData) {
        return Single.just(cachedData == null || isDataEmpty(cachedData));
    }

    // Load data from local cache
    protected abstract Observable<ResultType> loadFromCache();

    // Error handling method
    protected Resource<ResultType> handleError(Throwable throwable) {
        return Resource.error(throwable.getMessage(), null);
    }

    // Helper method to check if data is empty
    protected boolean isDataEmpty(ResultType data) {
        if (data == null) return true;

        if (data instanceof java.util.Collection) {
            return ((java.util.Collection<?>) data).isEmpty();
        }

        return false;
    }
}