package com.songthematic.songthemes.domain;

import java.util.List;

public record Song(String artist, String songTitle, String releaseTitle, String releaseType, List<String> themes) {
    public Song {
        if (artist.isBlank()) {
            throw new IllegalArgumentException();
        }
    }
}