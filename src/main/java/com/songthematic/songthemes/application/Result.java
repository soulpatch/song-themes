package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Result {

    private final boolean isSuccess;
    private List<Song> songs = new ArrayList<>();
    private List<String> failureMessages = new ArrayList<>();

    public Result(Set<Song> songs) {
        this.songs.addAll(songs);
        isSuccess = true;
    }

    public Result(List<String> failureMessage) {
        this.failureMessages.addAll(failureMessage);
        isSuccess = false;
    }

    public static Result success(Song song) {
        return new Result(Set.of(song));
    }

    public static Result failure(String message) {
        return new Result(Collections.singletonList(message));
    }

    public static Result success(List<Song> songs) {
        return new Result(Set.copyOf(songs));
    }

    public static Result failure(List<String> failureMessages) {
        return new Result(failureMessages);
    }

    public List<Song> songs() {
        return List.copyOf(songs);
    }

    public Song song() {
        return songs.getFirst();
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String failureMessage() {
        return failureMessages.getFirst();
    }

    public List<String> failureMessages() {
        return List.copyOf(failureMessages);
    }

}
