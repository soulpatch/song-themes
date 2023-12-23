package com.songthematic.songthemes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SongThemesController {

    private final SongSearcher songSearcher;

    @Autowired
    public SongThemesController(SongSearcher songSearcher) {
        this.songSearcher = songSearcher;
    }

    @GetMapping("/theme-search")
    public String themeSearch(String requestedTheme, Model model) {
        List<String> foundSongs = songSearcher.byTheme(requestedTheme);
        List<SongView> songViews = foundSongs
                .stream()
                .map(SongView::new)
                .toList();

        model.addAttribute("emptySearchResults", foundSongs.isEmpty());
        model.addAttribute("searchResults", songViews);
        return "theme-search-results";
    }
}
