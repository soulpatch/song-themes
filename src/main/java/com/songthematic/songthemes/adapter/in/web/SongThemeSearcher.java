package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.domain.SongSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SongThemeSearcher {

    private final SongSearcher songSearcher;

    @Autowired
    public SongThemeSearcher(SongSearcher songSearcher) {
        this.songSearcher = songSearcher;
    }

    @GetMapping("/theme-search")
    public String themeSearch(@RequestParam("requestedTheme") String requestedTheme, Model model) {
        List<String> foundSongs = songSearcher.songTitlesByTheme(requestedTheme);
        List<SongView> songViews = foundSongs
                .stream()
                .map(SongView::new)
                .toList();

        model.addAttribute("searchResults", songViews);

        if (foundSongs.isEmpty()) {
            return "theme-search-no-results";
        } else {
            return "theme-search-has-results";
        }
    }
}
