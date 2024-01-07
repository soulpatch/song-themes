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
        songRepository = SongRepository.create(new ArrayList<Song>());
    }

    public SongService(SongRepository songRepository) {
        songSearcher = SongSearcher.createSongSearcher(songRepository.allSongs());
        this.songRepository = songRepository;
    }

    public List<Song> searchByTheme(String theme) {
        return songSearcher.byTheme(theme);
    }

    public void addSong(Song song) {
        songRepository.add(song);
        songSearcher = songSearcher.add(song);
    }

    public void importSongs(String csvSongs) {
        CsvSongParser csvSongParser = new CsvSongParser();
        List<Song> songs = csvSongParser.parse(csvSongs);
        songs.forEach(this::addSong);
    }
}
