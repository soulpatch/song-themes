package com.songthematic.songthemes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SongThemesController {

    @GetMapping("/theme-search")
    public String themeSearch() {
        return "theme-search-results";
    }
}
