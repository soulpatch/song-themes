package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.application.InMemorySongRepository;
import com.songthematic.songthemes.application.SongService;
import com.songthematic.songthemes.domain.Song;
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
    void successPageShowsSongsImported() throws Exception {
        InMemorySongRepository repository = InMemorySongRepository.createEmpty();
        SongService songService = new SongService(repository);
        SongImporter songImporter = new SongImporter(songService);

        String tsvSongs = """
                Artist\tSong Title\tRelease Title\tRelease Type\tRecord Label\tNotes\tTheme1\tTheme2\tTheme3\tTheme4\tContributor
                DontCareArtist\tDontCareSongTitle\tDontCareReleaseTitle\tDontCareReleaseType\tDontCareRecordLabel\tSkippedNotes\tThank You\t\t\t\tDontCareContributor
                DontCareArtist2\tDontCareSongTitle2\tDontCareReleaseTitle2\tDontCareReleaseType2\tDontCareRecordLabel2\tSkippedNotes2\tThank You2\t\t\t\tDontCareContributor2
                """;
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        String redirectPage = songImporter.handleSongImport(tsvSongs, redirectAttributes);

        assertThat(redirectPage)
                .isEqualTo("redirect:/contributor/song-import-success");

        assertThat(successMessageFrom(redirectAttributes))
                .isEqualTo("Successfully imported 2 song(s)");

        List<Song> songs = repository.allSongs().toList();
        assertThat(songViewsFrom(redirectAttributes))
                .containsExactlyElementsOf(SongView.from(songs));
    }

    private List<SongView> songViewsFrom(RedirectAttributesModelMap redirectAttributes) {
        return (List<SongView>) redirectAttributes.getFlashAttributes().get("songViews");
    }

    @Test
    void songImportHandlesNullText() throws Exception {
        SongImporter songImporter = new SongImporter(SongService.createNull());

        String redirectPage = songImporter.handleSongImport(null, new RedirectAttributesModelMap());

        assertThat(redirectPage)
                .isEqualTo("redirect:/contributor/song-import");
    }

    @Test
    void songImportAddsFailureMessagesForFailedImport() throws Exception {
        SongImporter songImporter = new SongImporter(SongService.createNull());
        String tsvTwoMalformedSongs = """
                        Artist\tSong Title\tRelease Title\tTheme1\tTheme2
                        Blue Oyster Cult\tDon't Fear The Reaper\tAgents of Fortune
                        Kinks\tAround the Dial\tGive The People What They Want
                        """;

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String redirectPage = songImporter.handleSongImport(tsvTwoMalformedSongs, redirectAttributes);

        assertThat(redirectPage)
                .isEqualTo("redirect:/contributor/song-import");
        assertThat(failureMessagesFrom(redirectAttributes))
                .hasSize(2);
        assertThat(originalTextAreaContent(redirectAttributes))
                .isEqualTo(tsvTwoMalformedSongs);
    }

    // 1a) completely missing header row -> one failure message
    // 1b) missing a required column
    // 2) if header row correct, missing required cell(s) -> one failure message row?
    // artist, title, theme1
    // "", "foo", ""

    // shift toward test naming format: expectationWhenContext

    @Test
    void singleFailureMessageWhenMissingHeaderRow() throws Exception {
        SongImporter songImporter = new SongImporter(SongService.createNull());

        String missingHeaderRow = """
                Blue Oyster Cult\tDon't Fear The Reaper\tAgents of Fortune
                Kinks\tAround the Dial\tGive The People What They Want
                Howlin' Wolf\tEvil\tHis Best: The Chess 50th Anniversary Collection
                """;

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        songImporter.handleSongImport(missingHeaderRow, redirectAttributes);

        assertThat(failureMessagesFrom(redirectAttributes))
                .hasSize(1);
    }

    private String originalTextAreaContent(RedirectAttributes redirectAttributes) {
        return (String) redirectAttributes.getFlashAttributes().get("tsvSongs");
    }

    private List<String> failureMessagesFrom(RedirectAttributes redirectAttributes) {
        return (List<String>) redirectAttributes.getFlashAttributes().get("failureMessages");
    }

    private String successMessageFrom(RedirectAttributesModelMap redirectAttributes) {
        return (String) redirectAttributes.getFlashAttributes().get("successMessage");
    }
}