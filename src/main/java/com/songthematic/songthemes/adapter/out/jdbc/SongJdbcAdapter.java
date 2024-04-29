package com.songthematic.songthemes.adapter.out.jdbc;

import com.songthematic.songthemes.application.port.SongRepository;
import com.songthematic.songthemes.domain.Song;

import java.util.List;
import java.util.stream.Stream;

public class SongJdbcAdapter implements SongRepository {
    private final SongJdbcRepository songJdbcRepository;

    public SongJdbcAdapter(SongJdbcRepository songJdbcRepository) {

        this.songJdbcRepository = songJdbcRepository;
    }

    @Override
    public Stream<Song> allSongs() {
        return songJdbcRepository.findAll()
                                 .stream()
                                 .map(SongDbo::toDomain);
    }

    @Override
    public void add(Song song) {
        songJdbcRepository.save(SongDbo.from(song));
    }

    @Override
    public List<Song> findByTheme(String requestedTheme) {
        return songJdbcRepository.findByThemeIgnoreCase(requestedTheme)
                                 .stream()
                                 .map(SongDbo::toDomain)
                                 .toList();
    }
}
