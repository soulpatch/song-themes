package com.songthematic.songthemes.application;

import com.songthematic.songthemes.application.port.SongRepository;
import com.songthematic.songthemes.domain.Song;

import java.util.List;

public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public static SongService createNull() {
        return new SongService(InMemorySongRepository.createEmpty());
    }

    public List<Song> searchByTheme(String theme) {
        return songRepository.findByTheme(theme);
    }

    public void addSong(Song song) {
        songRepository.add(song);
    }

    public Result<Song> importSongs(String tsvSongs) {
        TsvSongParser tsvSongParser = new TsvSongParser();
        Result<Song> result = tsvSongParser.parseAll(tsvSongs);
        result.values().forEach(this::addSong);
        return result;
    }
}
