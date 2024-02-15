package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SongImporter {

    private final SongService songService;

    @Autowired
    public SongImporter(SongService songService) {
        this.songService = songService;
    }

    @GetMapping("/song-import")
    public String songImport() {
        return "song-import";
    }

    @PostMapping("/song-import")
    public String handleSongImport(String tsvSongs) {
        if (tsvSongs == null) {
            return "redirect:/song-import";
        }
        songService.importSongs(tsvSongs);
        return "redirect:/";
    }
}
