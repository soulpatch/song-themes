package com.songthematic.songthemes.application.port;

import com.songthematic.songthemes.domain.Song;

import java.util.List;
import java.util.stream.Stream;

public interface SongRepository {

    Stream<Song> allSongs();

    void add(Song song);

    List<Song> findByTheme(String requestedTheme);
}
