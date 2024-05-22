package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.Result;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.domain.Song;
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

    @GetMapping("/contributor/song-import")
    public String songImport() {
        return "song-import";
    }

    @GetMapping("/contributor/song-import-success")
    public String songImportSuccess() {
        return "song-import-success";
    }

    @PostMapping("/contributor/song-import")
    public String handleSongImport(@RequestParam("tsvSongs") String tsvSongs, RedirectAttributes redirectAttributes) {
        if (tsvSongs == null) {
            return "redirect:/contributor/song-import";
        }
        Result<Song> result = songService.importSongs(tsvSongs); //? confused 1 time by Result<Song>
        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", "Successfully imported %s song(s)".formatted(result.values().size()));
            return "redirect:/contributor/song-import-success";
        }
        redirectAttributes.addFlashAttribute("failureMessages", result.failureMessages());
        redirectAttributes.addFlashAttribute("tsvSongs", tsvSongs);

        return "redirect:/contributor/song-import";
    }
}
