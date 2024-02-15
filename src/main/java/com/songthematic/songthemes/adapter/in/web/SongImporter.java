package com.songthematic.songthemes.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SongImporter {

    @GetMapping("/song-import")
    public String songImport() {
        return "song-import";
    }

    @PostMapping("/song-import")
    public String handleSongImport() {
        return "redirect:/";
    }
}
