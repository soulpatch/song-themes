package com.songthematic.songthemes.application;

import com.songthematic.songthemes.domain.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class TsvSongParser {

    public static final int MAX_COLUMNS_TO_PARSE = 10;

    public Result<Song> parseAll(String tsvSongs) {
        if (tooFewLinesIn(tsvSongs)) {
            return Result.failure("Must have at least two rows of import data");
        }
        List<String> streamList = tsvSongs.lines().toList();
        String header = streamList.getFirst();
        Result<ColumnMapper> result = ColumnMapper.create(header);
        if (result.isFailure()) {
            return Result.failure(result.failureMessages());
        }
        ColumnMapper columnMapper = result.values().getFirst();
        Map<Boolean, List<Result<Song>>> partition = streamList
                .stream()
                .skip(1)
                .filter(not(String::isBlank))
                .map(tsvSong -> parseSong(tsvSong, columnMapper))
                .collect(Collectors.partitioningBy(Result::isSuccess));

        if (hasNoFailures(partition)) {
            List<Song> songs = mapToSongsFrom(partition);
            return Result.success(songs);
        }
        List<String> failureMessages = mapToFailureMessagesFrom(partition);
        return Result.failure(failureMessages);
    }

    public Result<Song> parseSong(String tsvSong, ColumnMapper columnMapper) {
        String[] columns = tsvSong.split("\t", MAX_COLUMNS_TO_PARSE);

        Result<String> result = columnMapper.validateColumnsMatch(columns);
        if (result.isFailure()) {
            return Result.failure(result.failureMessages());
        }

        Result<String> artist = columnMapper.extractColumn(columns, "Artist");
        Result<String> songTitle = columnMapper.extractColumn(columns, "Song Title");
        Result<String> releaseTitle = columnMapper.extractColumn(columns, "Release Title");
        Result<String> releaseType = columnMapper.extractColumn(columns, "Release Type");

        Result<String> theme1Result = columnMapper.extractColumn(columns, "Theme1");

        List<String> themes = extractThemes(columnMapper, columns);
        if (artist.isSuccess() && songTitle.isSuccess() && theme1Result.isSuccess()) {
            return Result.success(new Song(artist.values().getFirst(),
                                           songTitle.values().getFirst(),
                                           releaseTitle.values().getFirst(),
                                           releaseType.values().getFirst(), themes));
        }
        List<String> failureMessages = new ArrayList<>();
        failureMessages.addAll(artist.failureMessages());
        failureMessages.addAll(songTitle.failureMessages());
        failureMessages.addAll(theme1Result.failureMessages());
        return Result.failure(failureMessages);
    }

    private List<String> extractThemes(ColumnMapper columnMapper, String[] columns) {
        List<String> themeHeaders = List.of("Theme1", "Theme2", "Theme3", "Theme4");
        return themeHeaders.stream()
                           .map(themeHeader -> columnMapper.extractColumn(columns, themeHeader))
                           .filter(Result::isSuccess)
                           .flatMap(stringResult -> stringResult.values().stream())
                           .filter(not(String::isEmpty))
                           .toList();
    }

    private boolean tooFewLinesIn(String tsvSongs) {
        return tsvSongs.lines().count() <= 1;
    }

    private List<String> mapToFailureMessagesFrom(Map<Boolean, List<Result<Song>>> partition) {
        return partition
                .get(Boolean.FALSE)
                .stream()
                .flatMap((Result<Song> result) -> result.failureMessages().stream())
                .toList();
    }

    private List<Song> mapToSongsFrom(Map<Boolean, List<Result<Song>>> partition) {
        return partition
                .get(Boolean.TRUE)
                .stream()
                .flatMap(result -> result.values().stream())
                .toList();
    }

    private boolean hasNoFailures(Map<Boolean, List<Result<Song>>> partition) {
        return partition.get(Boolean.FALSE).isEmpty();
    }

}
