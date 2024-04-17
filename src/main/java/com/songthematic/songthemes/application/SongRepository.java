package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.*;
import java.util.stream.Stream;

public class SongRepository {
    private final List<Song> songs;
    private final Map<String, List<Song>> themeToSongsMap = new HashMap<>();

    private SongRepository(List<Song> songs) {
        this.songs = songs;
        index();
    }

    private void index() {
        themeToSongsMap.clear();

        for (Song song : songs) {
            for (String theme : song.themes()) {
                themeToSongsMap.computeIfAbsent(theme.toLowerCase(), ignored -> new ArrayList<>())
                               .add(song);
            }
        }
    }

    public static SongRepository create(List<Song> songList) {
        SongRepository songRepository = new SongRepository(songList);
        return songRepository;
    }

    public static SongRepository createEmpty() {
        return create(new ArrayList<>());
    }

    public Stream<Song> allSongs() {
        return songs.stream();
    }

    public void add(Song song) {
        songs.add(song);
        index();
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

}