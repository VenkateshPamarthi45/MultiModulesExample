package com.udacity.gradle.builditbigger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LiveDataResource<T> {
    Status status;
    public T data;
    public String errorMessage;

    public LiveDataResource(Status status, T data, String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> LiveDataResource<T> success(@NonNull T data) {
        return new LiveDataResource<>(Status.SUCCESS, data, null);
    }

    public static <T> LiveDataResource<T> error(String msg, @Nullable T data) {
        return new LiveDataResource<>(Status.ERROR, data, msg);
    }

    public static <T> LiveDataResource<T> loading(@Nullable T data) {
        return new LiveDataResource<>(Status.LOADING, data, null);
    }
    enum Status{
        SUCCESS,
        LOADING,
        ERROR
    }
}
