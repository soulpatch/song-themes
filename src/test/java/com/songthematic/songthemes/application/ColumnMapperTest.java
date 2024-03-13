package com.songthematic.songthemes.application;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

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
    void exceptionThrownWhenHeaderColumnCountDoesNotMatchDataColumnCount() throws Exception {
        ColumnMapper columnMapper = new ColumnMapper("One\tTwo\tThree\tFour");

        String[] columns = {"1", "2", "3", "4", "5", "6"};

        assertThatIllegalArgumentException()
                .isThrownBy(() -> columnMapper.extractColumn(columns, "Song Title"))
                .withMessage("Header column count of 4 does not match data column count of 6");
    }
}