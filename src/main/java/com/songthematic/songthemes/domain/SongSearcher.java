package com.songthematic.songthemes.domain;

import java.util.*;
import java.util.stream.Stream;

public class SongSearcher {

    private final Map<String, List<Song>> themeToSongsMap = new HashMap<>();

    private SongSearcher(Song... songs) {
        index(Arrays.asList(songs));
    }

    private void index(List<Song> songList) {
        for (Song song : songList) {
            for (String theme : song.themes()) {
                themeToSongsMap.computeIfAbsent(theme.toLowerCase(), ignored -> new ArrayList<>())
                               .add(song);
            }
        }
    }

    public static SongSearcher createSongSearcher(Song... songs) {
        return new SongSearcher(songs);
    }

    public static SongSearcher createSongSearcher(Stream<Song> songs) {
        return new SongSearcher(songs.toArray(Song[]::new));
    }

    public static SongSearcher withOneDefaultSongAndTheme(String theme) {
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
                                                 .flatMap(Collection::stream)
                                                 .distinct();
        Song[] songs = Stream.concat(songStream, Stream.of(song))
                             .toArray(Song[]::new);
        return createSongSearcher(songs);
    }
}
