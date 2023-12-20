package com.songthematic.songthemes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SongThemesControllerTest {

    @Test
    void postSearchRedirectsToSearchResults() throws Exception {
        SongThemesController songThemesController = new SongThemesController();

        String redirectPage = songThemesController.themeSearch();

        assertThat(redirectPage)
                .isEqualTo("redirect:/theme-search-results");
    }
}