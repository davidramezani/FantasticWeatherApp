package com.david.fantasticweatherapp.data.api;

import com.david.fantasticweatherapp.data.models.local.enums.ErrorType;

public abstract class Resource<T> {
    private Resource() {
    }

    public final static class Success<T> extends Resource<T> {
        private final T data;

        public T getData() {
            return data;
        }

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Loading<T> extends Resource<T> {
        public Loading() {
        }
    }

    public static final class Error<T> extends Resource<T> {

        private final String message;
        private final ErrorType errorType;

        public String getMessage() {
            return message;
        }

        public ErrorType getErrorType() {
            return errorType;
        }

        public Error(String message, ErrorType errorType) {
            this.message = message;
            this.errorType = errorType;
        }
    }
}

