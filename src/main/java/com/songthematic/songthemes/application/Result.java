package com.songthematic.songthemes.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Result<T> {

    private final boolean isSuccess;
    private List<T> values = new ArrayList<>();
    private List<String> failureMessages = new ArrayList<>();

    private Result(Set<T> values) {
        this.values.addAll(values);
        isSuccess = true;
    }

    private Result(List<String> failureMessage) {
        this.failureMessages.addAll(failureMessage);
        isSuccess = false;
    }

    static <T> Result<T> success(T value) {
        return new Result<>(Set.of(value));
    }

    static <T> Result<T> success(List<T> values) {
        return new Result<>(Set.copyOf(values));
    }

    static <T> Result<T> failure(String message) {
        return new Result<>(Collections.singletonList(message));
    }

    static <T> Result<T> failure(List<String> failureMessages) {
        return new Result<>(failureMessages);
    }

    public List<T> values() {
        return List.copyOf(values);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public List<String> failureMessages() {
        return List.copyOf(failureMessages);
    }
}
