package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.SongFactory;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.domain.Song;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongThemeSearcherTest {

    @ParameterizedTest(name = "requestedTheme is \"{0}\"")
    @ValueSource(strings = {"", " "})
    void emptySearchNavigatesToSearchHome(String requestedTheme) throws Exception {
        SongThemeSearcher songThemeSearcher = new SongThemeSearcher(SongService.createNull());

        String viewName = songThemeSearcher.themeSearch(requestedTheme, new ConcurrentModel());

        assertThat(viewName)
                .isEqualTo("theme-search-home");
    }

    @Test
    void searchReturnsModelWithEmptySearchResults() throws Exception {
        String theme = "new years";
        String songTitle = "auld lang syne";
        SongThemeSearcher songThemeSearcher = createSongThemesController(SongFactory.createSong(songTitle, theme));

        Model model = new ConcurrentModel();
        String viewName = songThemeSearcher.themeSearch("christmas", model);

        assertThat(viewName)
                .isEqualTo("theme-search-no-results");
    }

    @Test
    void searchReturnsModelWithNonEmptySearchResults() throws Exception {
        String theme = "new years";
        SongThemeSearcher songThemeSearcher = createSongThemesController(SongFactory.createSong("auld lang syne", theme),
                                                                         SongFactory.createSong("New Year's Eve In A Haunted House", theme));

        Model model = new ConcurrentModel();
        String viewName = songThemeSearcher.themeSearch("new years", model);

        List<SongView> searchResults = (List<SongView>) model.getAttribute("searchResults");
        assertThat(viewName)
                .isEqualTo("theme-search-has-results");
        assertThat(searchResults)
                .isNotEmpty();
    }

    @NotNull
    private static SongThemeSearcher createSongThemesController(Song... songs) {
        SongService songService = SongService.createNull();
        Arrays.stream(songs).forEach(songService::addSong);
        return new SongThemeSearcher(songService);
    }
}