package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TsvSongParser {

    public static final int MAX_COLUMNS_TO_PARSE = 10;
    public static final int MINIMUM_COLUMNS = 9;

    public List<Song> parse(String tsvSongs) {
        return tsvSongs.lines()
                       .map(this::parseSong)
                       .toList();
    }

    public Song parseSong(String tsvSong) {
        String[] columns = tsvSong.split("\t", MAX_COLUMNS_TO_PARSE);
        requireAtLeastNineColumns(columns);

        String artist = columns[0];
        String songTitle = columns[1];
        String releaseTitle = columns[2];
        String releaseType = columns[3];
        List<String> themes = parseThemes(columns);
        Song song = new Song(artist, songTitle, releaseTitle, releaseType, themes);
        return song;
    }

    private void requireAtLeastNineColumns(String[] columns) {
        if (columns.length < MINIMUM_COLUMNS) {
            throw new NotEnoughColumns("Number of columns was: " + columns.length
                                               + ", must have at least " + MINIMUM_COLUMNS
                                               + ", row contains: " + Arrays.toString(columns));
        }
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
