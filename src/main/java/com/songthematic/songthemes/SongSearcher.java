package com.songthematic.songthemes;

import java.util.*;

public class SongSearcher {

    private final Map<String, List<Song>> themeToSongsMap = new HashMap<>();

    private SongSearcher(Song... songs) {
        for (Song song : songs) {
            List<Song> songList;
            if (themeToSongsMap.containsKey(song.theme().toLowerCase())) {
                String normalizedTheme = song.theme().toLowerCase();
                songList = themeToSongsMap.get(normalizedTheme);
            } else {
                songList = new ArrayList<>();
            }
            songList.add(song);
            themeToSongsMap.put(song.theme().toLowerCase(), songList);
        }

        // TRY #2 later putIfAbsent
        // TRY #3 Collectors groupingBy

    }

    public static SongSearcher createSongSearcher(Song... songs) {
        return new SongSearcher(songs);
    }

    public static SongSearcher withOneSongForTheme(String theme) {
        return new SongSearcher(new Song(theme, "Song with theme " + theme));
    }

    public static SongSearcher withOneSong() {
        return createSongSearcher(new Song("new years", "auld lang syne"));
    }

    public List<String> byTheme(String requestedTheme) {
        List<Song> matchingSongs = themeToSongsMap.get(requestedTheme.toLowerCase());

        if (matchingSongs != null) {
            return matchingSongs.stream()
                                .map(Song::title)
                                .toList();
        }

        return Collections.emptyList();
    }
}
