package com.songthematic.songthemes.application;

import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class ColumnMapper {
    private final List<String> headerColumns;
    private final Set<String> requiredColumns;

    public ColumnMapper(String header) {
        requiredColumns = Set.of("Artist", "Song Title", "Theme1");
        String[] parsedHeaderColumns = header.split("\t", TsvSongParser.MAX_COLUMNS_TO_PARSE);
        headerColumns = Arrays.asList(parsedHeaderColumns);
    }

    @NotNull
    Result<String> extractColumn(String[] rowColumns, String columnName) {
        if (headerColumnsDoNotMatch(rowColumns)) {
            return Result.failure("Number of columns was: %s, must have at least %s, row contains: %s"
                                          .formatted(rowColumns.length, headerColumns.size(), Arrays.toString(rowColumns)));
        }

        if (headerColumns.contains(columnName)) {
            int index = headerColumns.indexOf(columnName);
            String column = rowColumns[index];
            return Result.success(column);
        } else if (isOptionalColumn(columnName)) {
            return Result.success("");
        }
        return Result.failure("Missing required column: \"" + columnName + "\"");
    }

    private boolean isOptionalColumn(String columnName) {
        return !requiredColumns.contains(columnName);
    }

    private boolean headerColumnsDoNotMatch(String[] columns) {
        return headerColumns.size() != columns.length;
    }

}