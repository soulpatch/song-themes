package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class TsvSongParser {

    public static final int MAX_COLUMNS_TO_PARSE = 10;
    public static final int MINIMUM_COLUMNS = 4;

    public Result parseAll(String tsvSongs) {
        if (tooFewLinesIn(tsvSongs)) {
            return Result.failure("Must have at least two rows of import data");
        }
        Map<Boolean, List<Result>> partition = tsvSongs.lines()
                                                       .skip(1)
                                                       .filter(not(String::isBlank))
                                                       .map(this::parseSong)
                                                       .collect(Collectors.partitioningBy(Result::isSuccess));
        if (hasNoFailures(partition)) {
            List<Song> songs = mapToSongsFrom(partition);
            return Result.success(songs);
        }
        List<String> failureMessages = mapToFailureMessagesFrom(partition);
        return Result.failure(failureMessages);
    }

    private boolean tooFewLinesIn(String tsvSongs) {
        return tsvSongs.lines().count() <= 1;
    }

    private List<String> mapToFailureMessagesFrom(Map<Boolean, List<Result>> partition) {
        return partition
                .get(Boolean.FALSE)
                .stream()
                .flatMap((Result result) -> result.failureMessages().stream())
                .toList();
    }

    private List<Song> mapToSongsFrom(Map<Boolean, List<Result>> partition) {
        return partition
                .get(Boolean.TRUE)
                .stream()
                .flatMap(result -> result.songs().stream())
                .toList();
    }

    private boolean hasNoFailures(Map<Boolean, List<Result>> partition) {
        return partition.get(Boolean.FALSE).isEmpty();
    }

    public Result parseSong(String tsvSong) {
        String[] columns = tsvSong.split("\t", MAX_COLUMNS_TO_PARSE);
        if (columns.length < MINIMUM_COLUMNS) {
            return Result.failure("Number of columns was: "
                                          + columns.length
                                          + ", must have at least " + MINIMUM_COLUMNS
                                          + ", row contains: " + Arrays.toString(columns));
        }

        String artist = columns[0];
        String songTitle = columns[1];
        String releaseTitle = columns[2];
        String releaseType = columns[3];
        List<String> themes = parseThemes(columns);
        Song song = new Song(artist, songTitle, releaseTitle, releaseType, themes);
        return Result.success(song);
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
