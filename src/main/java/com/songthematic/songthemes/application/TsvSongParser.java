package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.function.Predicate.not;

public class TsvSongParser {

    public static final int MAX_COLUMNS_TO_PARSE = 10;
    public static final int MINIMUM_COLUMNS = 9;

    public List<Song> parse(String tsvSongs) {
        // goal: no partial parse, all or nothing
        // return Result<List<Song>>
        return tsvSongs.lines()
                       .filter(not(String::isBlank))
                       .map(this::parseSong)
                       .map(Result::value)
                       .toList();
    }

    public Result parseSong(String tsvSong) {
        // have parseSong translate into a Result
        // return Result<Song>
        String[] columns = tsvSong.split("\t", MAX_COLUMNS_TO_PARSE);
        try {
            requireAtLeastNineColumns(columns);
        } catch (NotEnoughColumns e) {
            return Result.failure();
        }

        String artist = columns[0];
        String songTitle = columns[1];
        String releaseTitle = columns[2];
        String releaseType = columns[3];
        List<String> themes = parseThemes(columns);
        Song song = new Song(artist, songTitle, releaseTitle, releaseType, themes);
        return Result.success(song);
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
