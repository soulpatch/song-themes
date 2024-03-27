package com.songthematic.songthemes.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ColumnMapperTest {

    @Test
    void emptyStringReturnedWhenColumnDoesntExist() throws Exception {
        ColumnMapper columnMapper = new ColumnMapper("Artist\tSong Title\tRelease Title\tRelease Type\tTheme1");

        String[] columns = {"Earth, Wind & Fire", "Gratitude", "ReleaseTitle", "ReleaseType", "Thank You"}; // array literal form
        String column = columnMapper.extractColumn(columns, "ColumnThatDoesNotExist");

        assertThat(column)
                .isEmpty();
    }

    @Test
    @Disabled("Enable once we return Result.failure for mismatched column count")
    void exceptionThrownWhenHeaderColumnCountDoesNotMatchDataColumnCount() throws Exception {
        String headerRow = "One\tTwo\tThree\tFour";
        ColumnMapper columnMapper = new ColumnMapper(headerRow);

        String[] columns = {"1", "2", "3", "4", "5", "6"};

        // assert that extractColumn returns a failure Result instead of an exception
        Result result = null;
        try {
            String column = columnMapper.extractColumn(columns, "Three");
//            result = Result.success();
        } catch (IllegalArgumentException e) {
            result = Result.failure("Header column count of 4 does not match data column count of 6");
        }

        assertThat(result.isSuccess())
                .isFalse();
    }
}