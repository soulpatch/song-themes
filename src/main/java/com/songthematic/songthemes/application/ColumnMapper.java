package com.songthematic.songthemes.application;

import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ColumnMapper {
    private final List<String> headerColumns;
    private static Set<String> requiredColumns = Set.of("Artist", "Song Title", "Theme1");

    private ColumnMapper(String[] parsedHeaderColumns) {
        headerColumns = Arrays.asList(parsedHeaderColumns);
    }

    static Result<ColumnMapper> create(String header) {
        String[] parsedHeaderColumns = header.split("\t", TsvSongParser.MAX_COLUMNS_TO_PARSE);
        Set<String> parsedHeader = Stream.of(parsedHeaderColumns).collect(Collectors.toSet());
        if (parsedHeader.containsAll(requiredColumns)) {
            return Result.success(new ColumnMapper(parsedHeaderColumns));
        }
        return Result.failure("Missing required header row, header was: " + Arrays.toString(parsedHeaderColumns));
    }

    public static ColumnMapper createColumnMapper(String header) {
        return create(header).values().getFirst();
//        return new ColumnMapper(header.split("\t", TsvSongParser.MAX_COLUMNS_TO_PARSE));
    }

    @NotNull
    Result<String> extractColumn(String[] rowColumns, String columnName) {
        if (headerColumns.contains(columnName)) {
            int index = headerColumns.indexOf(columnName);
            String column = rowColumns[index];
            return Result.success(column);
        } else if (isOptionalColumn(columnName)) {
            return Result.success("");
        }
        return Result.failure("Missing required column: \"" + columnName + "\"");
    }

    public Result<String> validateColumnsMatch(String[] rowColumns) {
        if (headerColumnsDoNotMatch(rowColumns)) {
            return Result.failure("Number of columns was %s, row contains: %s. Must have columns matching the %s header columns %s."
                                          .formatted(rowColumns.length, Arrays.toString(rowColumns), headerColumns.size(), headerColumns));
        }
        return Result.success("");
    }

    private boolean isOptionalColumn(String columnName) {
        return !requiredColumns.contains(columnName);
    }

    private boolean headerColumnsDoNotMatch(String[] columns) {
        return headerColumns.size() != columns.length;
    }
}