package com.songthematic.songthemes.application.port;

import com.songthematic.songthemes.domain.Song;

import java.util.List;

public interface SongRepository {
    void add(Song song);

    List<Song> findByTheme(String requestedTheme);
}
