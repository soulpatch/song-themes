package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.domain.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SongThemeSearcher {

    private final SongService songService;

    public SongThemeSearcher(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/theme-search")
    public String themeSearch(@RequestParam("requestedTheme") String requestedTheme, Model model) {
        List<SongView> songViews = songService.searchByTheme(requestedTheme)
                .stream()
                                              .map(Song::songTitle)
                .map(SongView::new)
                .toList();

        model.addAttribute("searchResults", songViews);

        if (songViews.isEmpty()) {
            return "theme-search-no-results";
        } else {
            return "theme-search-has-results";
        }
    }
}
