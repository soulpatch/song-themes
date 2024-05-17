package com.songthematic.songthemes.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMapperTest {

    public static ColumnMapper createColumnMapper(String header) {
        return ColumnMapper.create(header).values().getFirst();
    }

    @Test
    void emptyStringReturnedWhenOptionalColumnDoesNotExist() throws Exception {
        ColumnMapper columnMapper = createColumnMapper("Artist\tSong Title\tRelease Title\tTheme1");

        String[] columns = {"Earth, Wind & Fire", "Gratitude", "Greatest Hits", "Thank You"};
        String optionalColumnName = "Release Type";
        Result<String> result = columnMapper.extractColumn(columns, optionalColumnName);

        assertThat(result.values())
                .containsExactly("");
    }

    @ParameterizedTest
    @CsvSource({
            "Artist\tRelease Title\tTheme1, [Song Title]",
            "Artist\tRelease Title, '[Song Title, Theme1]'",
            "Release Title, '[Artist, Song Title, Theme1]'",
            "Song Title\tRelease Title\tTheme1, [Artist]"
    })
    void failureWhenMissingOneRequiredColumn(String header, String missingColumnNames) throws Exception {

        Result<ColumnMapper> result = ColumnMapper.create(header);
        String[] parsedHeaderColumns = header.split("\t", TsvSongParser.MAX_COLUMNS_TO_PARSE);

        assertThat(result.isSuccess())
                .as("Expected success to be false because required column is missing")
                .isFalse();
        assertThat(result.failureMessages())
                .containsExactly("Header is missing the required column(s): " + missingColumnNames + ", header was: " + Arrays.toString(parsedHeaderColumns));
    }

    @Test
    void failureWhenHeaderColumnCountDoesNotMatchDataColumnCount() throws Exception {
        String headerRow = "Artist\tSong Title\tTheme1\tTheme2";
        ColumnMapper columnMapper = createColumnMapper(headerRow);

        String[] columns = {"1", "2", "3", "4", "5", "6"};

        Result<String> result = columnMapper.validateColumnsMatch(columns);

        assertThat(result.isSuccess())
                .isFalse();
        assertThat(result.failureMessages())
                .containsExactly("Number of columns was 6, row contains: [1, 2, 3, 4, 5, 6]. Must have columns matching the 4 header columns [Artist, Song Title, Theme1, Theme2].");
    }
}