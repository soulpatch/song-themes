package com.songthematic.songthemes.application;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMapperTest {

    @Test
    void emptyStringReturnedWhenColumnDoesntExist() throws Exception {
        ColumnMapper columnMapper = new ColumnMapper("Artist\tSong Title\tRelease Title\tRelease Type\tTheme1");

        String[] columns = {"Earth, Wind & Fire", "Gratitude", "ReleaseTitle", "ReleaseType", "Thank You"}; // array literal form
        Result<String> result = columnMapper.extractColumn(columns, "ColumnThatDoesNotExist");

        assertThat(result.values())
                .containsExactly("");
    }

    @Test
    void returnFailureWhenHeaderColumnCountDoesNotMatchDataColumnCount() throws Exception {
        String headerRow = "One\tTwo\tThree\tFour";
        ColumnMapper columnMapper = new ColumnMapper(headerRow);

        String[] columns = {"1", "2", "3", "4", "5", "6"};

        Result<String> result = columnMapper.extractColumn(columns, "Three");

        assertThat(result.isSuccess())
                .isFalse();
        assertThat(result.failureMessages())
                .containsExactly("Header column count of 4 does not match data column count of 6");
    }

}