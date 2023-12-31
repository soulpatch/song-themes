package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.List;

public class SongRepository {
    private List<Song> songRepository;

    public List<Song> getSongRepository() {
        return songRepository;
    }

    public void setSongRepository(List<Song> songRepository) {
        this.songRepository = songRepository;
    }

    public SongRepository() {
    }
}