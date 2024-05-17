package com.songthematic.songthemes.domain;

import java.util.ArrayList;
import java.util.List;

public record Song(String artist, String songTitle, String releaseTitle, String releaseType, List<String> themes) {
    public Song {
        if (themes.isEmpty()) {
            throw new MissingSongAttribute("Theme list is empty");
        }

        String message = "These attributes were blank: ";
        List<String> invalidAttributes = new ArrayList<>();
        if (artist.isBlank()) {
            invalidAttributes.add("artist");
        }

        if (songTitle.isBlank()) {
            invalidAttributes.add("song title");
        }

        if (themes.stream().anyMatch(String::isBlank)) {
            invalidAttributes.add("theme");
        }

        if (!invalidAttributes.isEmpty()) {
            throw new MissingSongAttribute(message + String.join(", ", invalidAttributes));
        }
    }
}