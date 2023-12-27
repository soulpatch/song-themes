package com.songthematic.songthemes;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongSearcher {

    private final Map<String, List<Song>> songs = new HashMap<>();

    private SongSearcher(Song... song) {
        songs.put(song[0].theme().toLowerCase(), List.of(song[0]));
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
        List<Song> matchingSongs = songs.get(requestedTheme.toLowerCase());

        if (matchingSongs != null) {
            return matchingSongs.stream()
                    .map(Song::title)
                    .toList();
        }

        return Collections.emptyList();
    }
}
