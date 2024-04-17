package com.songthematic.songthemes.application;

import com.songthematic.songthemes.application.port.SongRepository;
import com.songthematic.songthemes.domain.Song;

import java.util.*;
import java.util.stream.Stream;

public class InMemorySongRepository implements SongRepository {
    private final List<Song> songs;
    private final Map<String, List<Song>> themeToSongsMap = new HashMap<>();

    private InMemorySongRepository(List<Song> songs) {
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

    public static InMemorySongRepository create(List<Song> songList) {
        InMemorySongRepository songRepository = new InMemorySongRepository(songList);
        return songRepository;
    }

    public static InMemorySongRepository createEmpty() {
        return create(new ArrayList<>());
    }

    public Stream<Song> allSongs() {
        return songs.stream();
    }

    @Override
    public void add(Song song) {
        songs.add(song);
        index();
    }

    public List<String> songTitlesByTheme(String requestedTheme) {
        List<Song> matchingSongs = findByTheme(requestedTheme);
        return matchingSongs.stream()
                            .map(Song::songTitle)
                            .toList();
    }

    @Override
    public List<Song> findByTheme(String requestedTheme) {
        return themeToSongsMap.getOrDefault(requestedTheme.toLowerCase(), Collections.emptyList());
    }
}