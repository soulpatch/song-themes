package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

public class Result {

    private final Song value;

    public Result(Song value) {
        this.value = value;
    }

    public static Result success(Song song) {
        return new Result(song);
    }

    public static Result failure() {
        return new Result(null);
    }

    public Song value() {
        return value;
    }

    public boolean isSuccess() {
        return false;
    }
}
