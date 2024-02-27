package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private List<Song> songs = new ArrayList<>();
    private List<String> failureMessages = new ArrayList<>();

    public Result(List<Song> songs) {
        this.songs.addAll(songs);
    }

    public Result(String failureMessage) {
        this.failureMessages.add(failureMessage);
    }

    public static Result success(Song song) {
        return new Result(List.of(song));
    }

    public static Result failure(String message) {
        return new Result(message);
    }

    public static Result success(List<Song> songs) {
        return new Result(songs);
    }

    public List<Song> songs() {
        return List.copyOf(songs);
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

    public List<String> failureMessages() {
        return List.copyOf(failureMessages);
    }

}
