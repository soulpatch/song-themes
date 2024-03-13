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
    public static final int MINIMUM_COLUMNS = 3;

    public Result parseAll(String tsvSongs) {
        if (tooFewLinesIn(tsvSongs)) {
            return Result.failure("Must have at least two rows of import data");
        }
        List<String> streamList = tsvSongs.lines().toList();
        String header = "";
        ColumnMapper columnMapper = new ColumnMapper(header);
        Map<Boolean, List<Result>> partition = streamList
                .stream()
                .skip(1)
                .filter(not(String::isBlank))
                .map(tsvSong -> parseSong(header, tsvSong, columnMapper))
                .collect(Collectors.partitioningBy(Result::isSuccess));
        if (hasNoFailures(partition)) {
            List<Song> songs = mapToSongsFrom(partition);
            return Result.success(songs);
        }
        List<String> failureMessages = mapToFailureMessagesFrom(partition);
        return Result.failure(failureMessages);
    }

    public Result parseSong(String header, String tsvSong, ColumnMapper columnMapper) {
        if (header.isEmpty()) { // TEMPORARY if-test until all callers "fixed"
            return parseSongWithoutHeader(tsvSong);
        } else {
            String[] columns = tsvSong.split("\t", MAX_COLUMNS_TO_PARSE);

            String artist = columnMapper.extractColumn(columns, "Artist");
            String songTitle = columnMapper.extractColumn(columns, "Song Title");
            String theme1 = columnMapper.extractColumn(columns, "Theme1");
            return Result.success(new Song(artist, songTitle, "", "", List.of(theme1)));
        }
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

    private Result parseSongWithoutHeader(String tsvSong) {
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
        String releaseType = "";
        if (columns.length > 3) {
            releaseType = columns[3];
        }
        if (columns.length > 4) {
            List<String> themes = parseThemes(columns);
            Song song = new Song(artist, songTitle, releaseTitle, releaseType, themes);
            return Result.success(song);
        } else {
            return Result.failure("Number of columns was: "
                                          + columns.length
                                          + ", must have at least 5, row contains: " + Arrays.toString(columns));
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
