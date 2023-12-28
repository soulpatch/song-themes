package com.songthematic.songthemes.domain;

import java.util.*;
import java.util.stream.Collectors;

public class SongSearcher {

    private final Map<String, List<Song>> themeToSongsMap = new HashMap<>();

    private SongSearcher(Song... songs) {
        themeToSongsMap.putAll(
                Arrays.stream(songs)
                      .collect(
                              Collectors.groupingBy(song -> song.theme().toLowerCase())
                      ));
    }

    public static SongSearcher createSongSearcher(Song... songs) {
        return new SongSearcher(songs);
    }

    public static SongSearcher withOneSongForTheme(String theme) {
        return new SongSearcher(new Song(theme, "Song with theme " + theme));
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
