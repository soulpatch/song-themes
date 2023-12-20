package com.songthematic.songthemes;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SongThemesControllerTest {

    @Test
    void getSearchReturnsSearchResultsView() throws Exception {
        SongThemesController songThemesController = new SongThemesController();

        String viewName = songThemesController.themeSearch();

        assertThat(viewName)
                .isEqualTo("theme-search-results");
    }

}