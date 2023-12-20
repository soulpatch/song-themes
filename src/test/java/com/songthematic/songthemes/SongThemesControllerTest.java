package com.songthematic.songthemes;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

class SongThemesControllerTest {

    @Test
    void searchReturnsSearchResultsView() throws Exception {
        SongThemesController songThemesController = new SongThemesController(new SongSearcher());

        Model model = new ConcurrentModel();
        String viewName = songThemesController.themeSearch(model);

        assertThat(viewName)
                .isEqualTo("theme-search-results");
    }

    @Test
    void searchReturnsModelWithNoSongsFoundAttribute() throws Exception {
        SongThemesController songThemesController = new SongThemesController(new SongSearcher());

        Model model = new ConcurrentModel();
        songThemesController.themeSearch(model);

        assertThat(model.getAttribute("emptySearchResults"))
                .isEqualTo(Boolean.TRUE);
    }
}