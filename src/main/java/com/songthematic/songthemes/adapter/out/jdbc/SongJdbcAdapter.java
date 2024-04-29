package com.songthematic.songthemes.adapter.out.jdbc;

import com.songthematic.songthemes.application.port.SongRepository;
import com.songthematic.songthemes.domain.Song;

import java.util.List;
import java.util.stream.Stream;

public class SongJdbcAdapter implements SongRepository {
    @Override
    public Stream<Song> allSongs() {
        return Stream.empty();
    }

    @Override
    public void add(Song song) {

    }

    @Override
    public List<Song> findByTheme(String requestedTheme) {
        return List.of();
    }
}
