package com.songthematic.songthemes.domain;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SongSearcher {

    private final Map<String, List<Song>> themeToSongsMap = new HashMap<>();

    private SongSearcher(Song... songs) {
        Stream<Song> songStream = Arrays.stream(songs);
        themeToSongsMap.putAll(
                indexSongsByTheme(songStream));
    }

    private static Map<String, List<Song>> indexSongsByTheme(Stream<Song> songStream) {
        Map<String, List<Song>> map = new HashMap<>();
        songStream.forEach(indexSongByThemes(map));
        return map;
    }

    private static Consumer<Song> indexSongByThemes(Map<String, List<Song>> map) {
        return song -> {
            for (String theme : song.themes()) {
                String normalizedTheme = theme.toLowerCase();
                List<Song> songs = map.getOrDefault(normalizedTheme, new ArrayList<>());
                songs.add(song);
                map.put(normalizedTheme, songs);
            }
        };
    }

    public static SongSearcher createSongSearcher(Song... songs) {
        return new SongSearcher(songs);
    }

    public static SongSearcher createSongSearcher(Stream<Song> songs) {
        return new SongSearcher(songs.toArray(Song[]::new));
    }

    public static SongSearcher withOneSongForTheme(String theme) {
        return new SongSearcher(new Song("artist", "songTitle", "releaseTitle", "Song with theme " + theme, List.of(theme)));
    }


    public List<String> songTitlesByTheme(String requestedTheme) {
        List<Song> matchingSongs = byTheme(requestedTheme);
        return matchingSongs.stream()
                            .map(Song::songTitle)
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
