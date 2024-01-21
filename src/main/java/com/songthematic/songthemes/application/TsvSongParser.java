package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.List;

public class TsvSongParser {

    public List<Song> parse(String tsvSongs) {
        String[] columns = tsvSongs.split("\t");
        Song song = new Song(columns[0], columns[1], columns[2], columns[3], List.of(columns[5]));
        return List.of(song);
    }
}
