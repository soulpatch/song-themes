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
    Result<String> extractColumn(String[] columns, String columnName) {
        Result<String> result;
        try {
            String column = "";
            requireMatchingColumnCount(columns);

            if (headerColumns.contains(columnName)) {
                int index = headerColumns.indexOf(columnName);
                column = columns[index];
            }
            result = Result.success(column);
        } catch (IllegalArgumentException e) {
            result = Result.failure(e.getMessage());
        }
        return result;
    }

    private void requireMatchingColumnCount(String[] columnsForRow) {
        if (headerColumns.size() != columnsForRow.length) {
            throw new IllegalArgumentException("Number of columns was: %s, must have at least %s, row contains: %s"
                                                       .formatted(columnsForRow.length, headerColumns.size(), Arrays.toString(columnsForRow)));
        }
    }
}