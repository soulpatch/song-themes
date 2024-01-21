package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.Collections;
import java.util.List;

public class TsvSongParser {

    public List<Song> parse(String tsvSongs) {
        String[] columns = tsvSongs.split("\t");
        Song song = new Song(columns[0], "", "", "", Collections.emptyList());
        return List.of(song);
    }
}
