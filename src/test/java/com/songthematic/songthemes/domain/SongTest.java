package com.songthematic.songthemes.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SongTest {
    @ParameterizedTest(name = "requestedTheme is \"{0}\"")
    @ValueSource(strings={"", "\s"})
    void artistShouldNotBeEmptyNorBlank(String artist) throws Exception {
        assertThatExceptionOfType(MissingSongAttribute.class)
                .isThrownBy((() -> new Song(artist, "songTitleDontCare", "releaseTitleDontCare", "releaseTypeDontCare", List.of("themeNameDontCare"))))
                .withMessage("These attributes were blank: artist");
    }

    @ParameterizedTest(name = "songTitle is \"{0}\"")
    @ValueSource(strings = {"", "\s"})
    void songTitleShouldNotBeEmptyNorBlank(String songTitle) throws Exception {
        assertThatExceptionOfType(MissingSongAttribute.class)
                .isThrownBy((() -> new Song("artistDontCare", songTitle, "releaseTitleDontCare", "releaseTypeDontCare", List.of("themeNameDontCare"))))
                .withMessage("These attributes were blank: song title");

    }

    @ParameterizedTest(name = "attribute is \"{0}\"")
    @ValueSource(strings = {"", "\s"})
    void artistAndSongTitleShouldNotBeEmptyNorBlank(String attribute) throws Exception {
        assertThatExceptionOfType(MissingSongAttribute.class)
                .isThrownBy((() -> new Song(attribute, attribute, "releaseTitleDontCare", "releaseTypeDontCare", List.of(attribute))))
                .withMessage("These attributes were blank: artist, song title, theme");

    }
}