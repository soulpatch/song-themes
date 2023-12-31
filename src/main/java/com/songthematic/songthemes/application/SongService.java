package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import com.songthematic.songthemes.domain.SongSearcher;

import java.util.ArrayList;
import java.util.List;

public class SongService {

    private final SongRepository songRepository;
    private SongSearcher songSearcher;

    public SongService() {
        songSearcher = SongSearcher.createSongSearcher();
        songRepository = new SongRepository();
        songRepository.setSongRepository(new ArrayList<>());
    }

    public SongService(SongRepository songRepository) {
        songSearcher = SongSearcher.createSongSearcher(songRepository.getSongRepository());
        this.songRepository = songRepository;
    }

    public List<Song> searchByTheme(String theme) {
        return songSearcher.byTheme(theme);
    }

    public void addSong(Song song) {
        songRepository.getSongRepository().add(song);
        songSearcher = songSearcher.add(song);
    }
}
