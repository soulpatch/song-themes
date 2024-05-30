package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.SongThemesConfig;
import com.songthematic.songthemes.adapter.out.jdbc.JdbcSongRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({SongThemesConfig.class})
@Tag("mvc")
@WithMockUser("fake-user-name")
public class SongImportMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JdbcSongRepository jdbcSongRepository;

    @Test
    public void getRequestToSongImportEndpointReturns200() throws Exception {
        mockMvc.perform(get("/contributor/song-import"))
               .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getRequestToSongImportSuccessEndpointReturns200() throws Exception {
        mockMvc.perform(get("/contributor/song-import-success"))
               .andExpect(status().is2xxSuccessful());
    }

    @Test
    void postRequestToSongImportEndpointRedirects() throws Exception {
        mockMvc.perform(post("/contributor/song-import")
                                .param("tsvSongs", "")
                                .with(csrf()))
               .andExpect(status().is3xxRedirection());
    }
}
