package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.Result;
import com.songthematic.songthemes.application.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String handleSongImport(@RequestParam("tsvSongs") String tsvSongs, RedirectAttributes redirectAttributes) {
        if (tsvSongs == null) {
            return "redirect:/song-import";
        }
        Result result = songService.importSongs(tsvSongs);
        if (result.isSuccess()) {
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("failureMessages", result.failureMessages());
        redirectAttributes.addFlashAttribute("tsvSongs", tsvSongs);

        return "redirect:/song-import";
    }
}
