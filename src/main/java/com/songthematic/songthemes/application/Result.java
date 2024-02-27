package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private List<Song> songs = new ArrayList<>();
    private List<String> failureMessages = new ArrayList<>();

    public Result(Song song) {
        this.songs.add(song);
    }

    public Result(String failureMessage) {
        this.failureMessages.add(failureMessage);
    }

    public static Result success(Song song) {
        return new Result(song);
    }

    public static Result failure(String message) {
        return new Result(message);
    }

    public Song song() {
        return songs.getFirst();
    }

    public boolean isSuccess() {
        return false;
    }

    public String failureMessage() {
        return failureMessages.getFirst();
    }
}
