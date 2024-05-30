package com.songthematic.songthemes.adapter.out.jdbc;

import com.songthematic.songthemes.application.port.SongRepository;
import com.songthematic.songthemes.domain.Song;

import java.util.List;
import java.util.stream.Stream;

public class SongJdbcAdapter implements SongRepository {
    private final JdbcSongRepository jdbcSongRepository;

    public SongJdbcAdapter(JdbcSongRepository jdbcSongRepository) {

        this.jdbcSongRepository = jdbcSongRepository;
    }

    @Override
    public Stream<Song> allSongs() {
        return jdbcSongRepository.findAll()
                                 .stream()
                                 .map(SongDbo::toDomain);
    }

    @Override
    public void add(Song song) {
        jdbcSongRepository.save(SongDbo.from(song));
    }

    @Override
    public List<Song> findByTheme(String requestedTheme) {
        return jdbcSongRepository.findByThemeIgnoreCase(requestedTheme)
                                 .stream()
                                 .map(SongDbo::toDomain)
                                 .toList();
    }
}
