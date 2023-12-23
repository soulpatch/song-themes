package com.songthematic.songthemes;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongThemesControllerTest {

    @Test
    void searchReturnsSearchResultsView() throws Exception {
        SongThemesController songThemesController = new SongThemesController(SongSearcher.withOneSong());

        Model model = new ConcurrentModel();
        String viewName = songThemesController.themeSearch("", model);

        assertThat(viewName)
                .isEqualTo("theme-search-results");
    }

    @Test
    void searchReturnsModelWithEmptySearchResults() throws Exception {
        String theme = "new years";
        String songTitle = "auld lang syne";
        SongThemesController songThemesController = createSongThemesController(new Song(theme, songTitle));

        Model model = new ConcurrentModel();
        songThemesController.themeSearch("christmas", model);

        assertThat(model.getAttribute("emptySearchResults"))
                .isEqualTo(Boolean.TRUE);
    }

    @Test
    @Disabled
    void searchReturnsModelWithNonEmptySearchResults() throws Exception {
        String theme = "new years";
        SongThemesController songThemesController = createSongThemesController(new Song(theme, "auld lang syne"),
                                                                               new Song(theme, "New Year's Eve In A Haunted House"));

        Model model = new ConcurrentModel();
        songThemesController.themeSearch("new years", model);

        assertThat(model.getAttribute("emptySearchResults"))
                .isEqualTo(Boolean.FALSE);
        List<SongView> searchResults = (List<SongView>) model.getAttribute("searchResults");
        assertThat(searchResults)
                .containsExactly(new SongView("auld lang syne"),
                                 new SongView("New Year's Eve In A Haunted House"));
    }

    @NotNull
    private static SongThemesController createSongThemesController(Song... songs) {
        return new SongThemesController(
                SongSearcher.createSongSearcher(songs[0]));
    }
}