package com.songthematic.songthemes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongSearcher {

    private final String theme;
    private final String song;
    private final Map<String, Song> songs = new HashMap<>();

    private SongSearcher(Song... song) {
        this.theme = song[0].theme();
        this.song = song[0].title();
        songs.put(song[0].theme().toLowerCase(), song[0]);
    }

    public static SongSearcher createSongSearcher(Song... songs) {
        return new SongSearcher(songs);
    }

    public static SongSearcher withOneSongForTheme(String theme) {
        return new SongSearcher(new Song(theme, "Song with theme "+ theme));
    }

    public static SongSearcher withOneSong() {
        return createSongSearcher(new Song("new years", "auld lang syne"));
    }

    public List<String> byTheme(String requestedTheme) {
        Song song = songs.get(requestedTheme.toLowerCase());

        if (song != null) {
            return List.of(song.title());
        }
        return Collections.emptyList();
    }
}
