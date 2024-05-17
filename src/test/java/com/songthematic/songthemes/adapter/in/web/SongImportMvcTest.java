package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Tag("io")
@WithMockUser("fake-user-name")
public class SongImportMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SongJdbcRepository songJdbcRepository;

    @Test
    public void getToSongImportEndpointReturns200() throws Exception {
        mockMvc.perform(get("/song-import"))
               .andExpect(status().is2xxSuccessful());
    }

    @Test
    void postToSongImportEndpointRedirects() throws Exception {
        mockMvc.perform(post("/song-import")
                                .param("tsvSongs", "")
                                .with(csrf()))
               .andExpect(status().is3xxRedirection());
    }
}
