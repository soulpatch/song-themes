package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;
import com.songthematic.songthemes.domain.SongSearcher;

import java.util.List;

public class SongService {

    private final SongRepository songRepository;
    private SongSearcher songSearcher;

    public SongService(SongRepository songRepository) {
        songSearcher = SongSearcher.createSongSearcher(songRepository.allSongs());
        this.songRepository = songRepository;
    }

    public static SongService createNull() {
        return new SongService(SongRepository.createEmpty());
    }

    public List<Song> searchByTheme(String theme) {
        return songSearcher.byTheme(theme);
    }

    public void addSong(Song song) {
        songRepository.add(song);
        songSearcher = songSearcher.add(song);
    }

    public Result importSongs(String tsvSongs) {
        TsvSongParser tsvSongParser = new TsvSongParser();
        Result result = tsvSongParser.parseAll(tsvSongs);
        result.songs().forEach(this::addSong);
        return result;
    }
}
