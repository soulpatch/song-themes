package com.songthematic.songthemes.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<String> songTitlesByTheme(String requestedTheme) {
        List<Song> matchingSongs = byTheme(requestedTheme);
        return matchingSongs.stream()
                            .map(Song::title)
                            .toList();
    }

    public List<Song> byTheme(String requestedTheme) {
        return themeToSongsMap.getOrDefault(requestedTheme.toLowerCase(), Collections.emptyList());
    }

    public SongSearcher add(Song song) {
        Stream<Song> songStream = themeToSongsMap.values()
                                                 .stream()
                                                 .flatMap(Collection::stream);
        Song[] songs = Stream.concat(songStream, Stream.of(song))
                                      .toArray(Song[]::new);
        return createSongSearcher(songs);
    }
}
