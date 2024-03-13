package com.songthematic.songthemes.application;

import java.util.Arrays;
import java.util.List;

public final class ColumnMapper {
    private final List<String> headerColumns;

    public ColumnMapper(String header) {
        String[] parsedHeaderColumns = header.split("\t", TsvSongParser.MAX_COLUMNS_TO_PARSE);
        headerColumns = Arrays.asList(parsedHeaderColumns);
    }

    String extractColumn(String[] columns, String columnName) {
        requireMatchingColumnCount(columns);

        if (headerColumns.contains(columnName)) {
            int index = headerColumns.indexOf(columnName);
            return columns[index];
        }
        return "";
    }

    private void requireMatchingColumnCount(String[] columns) {
        if (headerColumns.size() != columns.length) {
            throw new IllegalArgumentException("Header column count of %s does not match data column count of %s".formatted(headerColumns.size(), columns.length));
        }
    }
}