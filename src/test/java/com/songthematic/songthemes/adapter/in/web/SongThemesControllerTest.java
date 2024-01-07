package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.SongFactory;
import com.songthematic.songthemes.domain.Song;
import com.songthematic.songthemes.domain.SongSearcher;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongThemesControllerTest {

    @Test
    void searchReturnsModelWithEmptySearchResults() throws Exception {
        String theme = "new years";
        String songTitle = "auld lang syne";
        SongThemesController songThemesController = createSongThemesController(SongFactory.createSong(songTitle, theme));

        Model model = new ConcurrentModel();
        String viewName = songThemesController.themeSearch("christmas", model);

        assertThat(viewName)
                .isEqualTo("theme-search-no-results");
    }

    @Test
    void searchReturnsModelWithNonEmptySearchResults() throws Exception {
        String theme = "new years";
        SongThemesController songThemesController = createSongThemesController(SongFactory.createSong("auld lang syne", theme),
                                                                               SongFactory.createSong("New Year's Eve In A Haunted House", theme));

        Model model = new ConcurrentModel();
        String viewName = songThemesController.themeSearch("new years", model);

        List<SongView> searchResults = (List<SongView>) model.getAttribute("searchResults");
        assertThat(viewName)
                .isEqualTo("theme-search-has-results");
        assertThat(searchResults)
                .isNotEmpty();
    }

    @NotNull
    private static SongThemesController createSongThemesController(Song... songs) {
        return new SongThemesController(
                SongSearcher.createSongSearcher(songs));
    }
}