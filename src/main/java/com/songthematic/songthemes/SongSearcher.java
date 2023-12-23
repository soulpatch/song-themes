package com.songthematic.songthemes;

import java.util.Collections;
import java.util.List;

public class SongSearcher {

    private final String theme;
    private final String song;

    private SongSearcher(String theme, String song) {
        this.theme = theme;
        this.song = song;
    }

    public static SongSearcher createSongSearcher(Song song) {

        return new SongSearcher(song.theme(), song.songTitle());
    }

    public static SongSearcher withSongsForTheme(String theme) {
        return new SongSearcher(theme, "Song with theme "+ theme);
    }

    public static SongSearcher withOneSong() {
        return createSongSearcher(new Song("new years", "auld lang syne"));
    }

    public List<String> byTheme(String requestedTheme) {
        if (requestedTheme.equalsIgnoreCase(theme)) {
            return List.of(song);
        }
        return Collections.emptyList();
    }
}
