package com.songthematic.songthemes.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMapperTest {

    @Test
    void emptyStringReturnedWhenOptionalColumnDoesNotExist() throws Exception {
        ColumnMapper columnMapper = new ColumnMapper("Artist\tSong Title\tRelease Title\tTheme1");

        String[] columns = {"Earth, Wind & Fire", "Gratitude", "Greatest Hits", "Thank You"};
        String optionalColumnName = "Release Type";
        Result<String> result = columnMapper.extractColumn(columns, optionalColumnName);

        assertThat(result.values())
                .containsExactly("");
    }

    @ParameterizedTest
    @CsvSource({
            "Song Title, Missing required column: \"Song Title\", Artist\tRelease Title\tTheme1",
            "Artist, Missing required column: \"Artist\", Song Title\tRelease Title\tTheme1"
    })
    void failureWhenMissingOneRequiredColumn(String requiredColumnName, String failureMessage, String header) throws Exception {
        ColumnMapper columnMapper = new ColumnMapper(header);

        String[] columns = {"Earth, Wind & Fire", "Greatest Hits", "Thank You"};
        Result<String> result = columnMapper.extractColumn(columns, requiredColumnName);

        assertThat(result.isSuccess())
                .as("Expected success to be false because required column is missing")
                .isFalse();
        assertThat(result.failureMessages())
                .containsExactly(failureMessage);
    }

    @Test
    void twoFailureMessagesWhenMissingTwoRequiredColumns() throws Exception {

    }

    @Test
    void failureWhenHeaderColumnCountDoesNotMatchDataColumnCount() throws Exception {
        String headerRow = "One\tTwo\tThree\tFour";
        ColumnMapper columnMapper = new ColumnMapper(headerRow);

        String[] columns = {"1", "2", "3", "4", "5", "6"};

        Result<String> result = columnMapper.extractColumn(columns, "Three");

        assertThat(result.isSuccess())
                .isFalse();
        assertThat(result.failureMessages())
                .containsExactly("Number of columns was: 6, must have at least 4, row contains: [1, 2, 3, 4, 5, 6]");
    }
}