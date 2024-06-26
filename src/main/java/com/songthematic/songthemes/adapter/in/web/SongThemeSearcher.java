package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.application.port.ThemeFinder;
import com.songthematic.songthemes.domain.Song;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SongThemeSearcher {

    private final SongService songService;
    private final ThemeFinder themeFinder;

    public SongThemeSearcher(SongService songService, ThemeFinder themeFinder) {
        this.songService = songService;
        this.themeFinder = themeFinder;
    }

    @GetMapping("/themes")
    @ResponseBody
    public String autocompleteThemes(@RequestParam(value = "theme-query", defaultValue = "") String themeQuery) {
        if (themeQuery.isBlank()) {
            return "";
        }
        List<String> matchingThemes = themeFinder.startsWithIgnoringCase(themeQuery);
        return HtmlTransformer.convertThemesToHtml(matchingThemes);
    }

    @PostMapping("/selected-themes")
    @ResponseBody
    public String selectTheme(@RequestParam("theme") String theme) {
        return """
                <swap hx-swap-oob="beforeend" id="selected-themes-box">
                <li>%s</li>
                </swap>""".formatted(theme);
    }

    @GetMapping("/theme-search")
    public String themeSearch(@RequestParam(value = "requested-theme", required = false, defaultValue = "") String requestedTheme, Model model) {
        if (requestedTheme.isBlank()) {
            model.addAttribute("themes", themeFinder.allUniqueThemesAlphabetically());
            return "theme-search-home";
        }
        List<Song> foundSongs = songService.searchByTheme(requestedTheme);
        List<SongView> songViews = SongView.from(foundSongs);

        model.addAttribute("searchResults", songViews);

        if (songViews.isEmpty()) {
            return "theme-search-no-results";
        } else {
            return "theme-search-has-results";
        }
    }


}
