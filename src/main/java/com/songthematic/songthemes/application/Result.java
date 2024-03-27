package com.songthematic.songthemes.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Result<SUCCESS> {

    private final boolean isSuccess;
    private List<SUCCESS> values = new ArrayList<>();
    private List<String> failureMessages = new ArrayList<>();

    private Result(Set<SUCCESS> values) {
        this.values.addAll(values);
        isSuccess = true;
    }

    private Result(List<String> failureMessage) {
        this.failureMessages.addAll(failureMessage);
        isSuccess = false;
    }

    static <SUCCESS> Result<SUCCESS> success(SUCCESS value) {
        return new Result<>(Set.of(value));
    }

    static <SUCCESS> Result<SUCCESS> success(List<SUCCESS> values) {
        return new Result<>(Set.copyOf(values));
    }

    static <SUCCESS> Result<SUCCESS> failure(String message) {
        return new Result<>(Collections.singletonList(message));
    }

    static <SUCCESS> Result<SUCCESS> failure(List<String> failureMessages) {
        return new Result<>(failureMessages);
    }

    public List<SUCCESS> values() {
        return List.copyOf(values);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public List<String> failureMessages() {
        return List.copyOf(failureMessages);
    }
}
