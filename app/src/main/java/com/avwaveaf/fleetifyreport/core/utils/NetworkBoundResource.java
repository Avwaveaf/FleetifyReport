package com.avwaveaf.fleetifyreport.core.utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    public Observable<Resource<ResultType>> asObservable() {
        return Observable.defer(() -> loadFromCache()
                .flatMap(result -> {
                    if (result == null || shouldFetch().blockingGet()) {
                        // Return the entire Resource object, not just the message
                        return createCall()
                                .doOnNext(response -> saveCallResult(processResponse(response)))
                                .map(response -> Resource.success(processResponse(response)))
                                .onErrorReturn(NetworkErrorUtil::handleError);
                    } else {
                        return Observable.just(Resource.success(result));
                    }
                }));
    }

    protected abstract Observable<RequestType> createCall();

    protected abstract ResultType processResponse(RequestType response);

    protected abstract void saveCallResult(ResultType item);

    protected abstract Single<Boolean> shouldFetch();

    protected abstract Observable<ResultType> loadFromCache();


}