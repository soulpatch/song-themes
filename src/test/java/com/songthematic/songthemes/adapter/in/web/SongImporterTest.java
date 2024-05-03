package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.InMemorySongRepository;
import com.songthematic.songthemes.application.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SongImporterTest {

    @Test
    void songImportReturnsTemplateName() throws Exception {
        SongImporter songImporter = new SongImporter(SongService.createNull());

        String viewName = songImporter.songImport();

        assertThat(viewName)
                .isEqualTo("song-import");
    }

    @Test
    void songImportPutsSongInRepository() throws Exception {
        InMemorySongRepository repository = InMemorySongRepository.createEmpty();
        SongService songService = new SongService(repository);
        SongImporter songImporter = new SongImporter(songService);

        String tsvSongs = """
                Artist\tSong Title\tRelease Title\tRelease Type\tRecord Label\tNotes\tTheme1\tTheme2\tTheme3\tTheme4\tContributor
                DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tSkippedNotes\tThank You\t\t\t\tDontCareContributor
                """;
        songImporter.handleSongImport(tsvSongs, new RedirectAttributesModelMap());

        assertThat(repository.allSongs())
                .hasSize(1);
    }

    @Test
    void songImportHandlesNullText() throws Exception {
        SongImporter songImporter = new SongImporter(SongService.createNull());

        String redirectPage = songImporter.handleSongImport(null, new RedirectAttributesModelMap());

        assertThat(redirectPage)
                .isEqualTo("redirect:/song-import");
    }

    @Test
    void songImportAddsFailureMessagesForFailedImport() throws Exception {
        SongImporter songImporter = new SongImporter(SongService.createNull());
        String tsvTwoMalformedSongs =
                """
                        Artist\tSong Title\tRelease Title\tTheme1\tTheme2
                        Blue Oyster Cult\tDon't Fear The Reaper\tAgents of Fortune
                        Kinks\tAround the Dial\tGive The People What They Want
                        """;

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String redirectPage = songImporter.handleSongImport(tsvTwoMalformedSongs, redirectAttributes);

        assertThat(redirectPage)
                .isEqualTo("redirect:/song-import");
        assertThat(failureMessages(redirectAttributes))
                .hasSize(2);
        assertThat(originalTextAreaContent(redirectAttributes))
                .isEqualTo(tsvTwoMalformedSongs);
    }

    private String originalTextAreaContent(RedirectAttributes redirectAttributes) {
        return (String) redirectAttributes.getFlashAttributes().get("tsvSongs");
    }

    private List<String> failureMessages(RedirectAttributes redirectAttributes) {
        return (List<String>) redirectAttributes.getFlashAttributes().get("failureMessages");
    }
}