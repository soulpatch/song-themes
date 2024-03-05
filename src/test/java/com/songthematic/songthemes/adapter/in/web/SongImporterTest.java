package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.SongRepository;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.application.TsvSongFactory;
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
        SongRepository repository = SongRepository.createEmpty();
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
        String tsvTwoMalformedSongs = TsvSongFactory.createTsvSongsWithHeader("""
                                                                                      Husker Du\tGreen Eyes
                                                                                      Kinks\tAround the Dial\tGive The People What They Want
                                                                                      """);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String redirectPage = songImporter.handleSongImport(tsvTwoMalformedSongs, redirectAttributes);

        assertThat(redirectPage)
                .isEqualTo("redirect:/song-import");
        List<String> failureMessages = (List<String>) redirectAttributes.getFlashAttributes().get("failureMessages");
        assertThat(failureMessages)
                .hasSize(2);
        String originalTextAreaContent = (String) redirectAttributes.getFlashAttributes().get("tsvSongs");
        assertThat(originalTextAreaContent)
                .isEqualTo(tsvTwoMalformedSongs);
    }
}