package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import com.songthematic.songthemes.domain.SongSearcher;

import java.util.Collections;
import java.util.List;

public class SongService {

    private final List<Song> songRepository;
    private SongSearcher songSearcher;

    public SongService() {
        songSearcher = SongSearcher.createSongSearcher();
        songRepository = Collections.emptyList();
    }

    public SongService(List<Song> songRepository) {
        songSearcher = SongSearcher.createSongSearcher(songRepository);
        this.songRepository = songRepository;
    }

    public List<Song> searchByTheme(String theme) {
        return songSearcher.byTheme(theme);
    }

    public void addSong(Song song) {
        songSearcher = songSearcher.add(song);
    }
}
