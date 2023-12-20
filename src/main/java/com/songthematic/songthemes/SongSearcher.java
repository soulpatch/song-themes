package com.songthematic.songthemes;

import java.util.Collections;
import java.util.List;

public class SongSearcher {

    private final String theme;
    private final String song;

    public SongSearcher(String theme, String song) {
        this.theme = theme;
        this.song = song;
    }

    public List<String> byTheme(String requestedTheme) {
        if (requestedTheme.equalsIgnoreCase(theme)) {
            return List.of(song);
        }
        return Collections.emptyList();
    }
}
