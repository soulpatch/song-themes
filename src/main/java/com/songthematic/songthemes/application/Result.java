package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

public class Result {

    private Song value;
    private String failureMessage;

    public Result(Song value) {
        this.value = value;
    }

    public Result(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public static Result success(Song song) {
        return new Result(song);
    }

    public static Result failure(String message) {
        return new Result(message);
    }

    public Song value() {
        return value;
    }

    public boolean isSuccess() {
        return false;
    }

    public String failureMessage() {
        return failureMessage;
    }
}
