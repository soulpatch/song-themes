package com.songthematic.songthemes.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class SongTest {
    @ParameterizedTest
    @ValueSource(strings={"", "\s"})
    void artistShouldNotBeEmptyNorBlank(String artist) throws Exception {
        assertThatIllegalArgumentException()
                .isThrownBy((()->new Song(artist, "songTitleDontCare", "releaseTitleDontCare", "releaseTypeDontCare", List.of("themeNameDontCare"))));
    }

}