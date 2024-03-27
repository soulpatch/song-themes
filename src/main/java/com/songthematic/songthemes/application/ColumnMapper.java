package com.songthematic.songthemes.application;

import jakarta.validation.constraints.NotNull;

import java.util.Arrays;
import java.util.List;

public final class ColumnMapper {
    private final List<String> headerColumns;

    public ColumnMapper(String header) {
        String[] parsedHeaderColumns = header.split("\t", TsvSongParser.MAX_COLUMNS_TO_PARSE);
        headerColumns = Arrays.asList(parsedHeaderColumns);
    }

    @NotNull
    Result<String> extractColumn(String[] rowColumns, String columnName) {
        if (headerColumnsDoNotMatch(rowColumns)) {
            return Result.failure("Number of columns was: %s, must have at least %s, row contains: %s"
                                          .formatted(rowColumns.length, headerColumns.size(), Arrays.toString(rowColumns)));
        }

        String column = "";
        if (headerColumns.contains(columnName)) {
            int index = headerColumns.indexOf(columnName);
            column = rowColumns[index];
        }
        return Result.success(column);
    }

    private boolean headerColumnsDoNotMatch(String[] columns) {
        return headerColumns.size() != columns.length;
    }

}