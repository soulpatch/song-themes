package com.songthematic.songthemes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SongThemesController {

    @PostMapping("/theme-search")
    public String themeSearch() {
        return "redirect:/";
    }
}
