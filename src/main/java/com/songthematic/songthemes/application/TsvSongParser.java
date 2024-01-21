package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.List;

public class TsvSongParser {

    public List<Song> parse(String tsvSongs) {
        String[] columns = tsvSongs.split("\t");

        String artist = columns[0];
        String songTitle = columns[1];
        String releaseTitle = columns[2];
        String releaseType = columns[3];
        List<String> themes = parseThemes(columns);
        Song song = new Song(artist, songTitle, releaseTitle, releaseType, themes);
        return List.of(song);
    }

    private List<String> parseThemes(String[] columns) {
        List<String> themes = new ArrayList<>();

        for (int i = 5; i <= 8; i++) {
            if (columns[i].isEmpty()) {
                break;
            }
            themes.add(columns[i]);
        }
        return themes;
    }
}
