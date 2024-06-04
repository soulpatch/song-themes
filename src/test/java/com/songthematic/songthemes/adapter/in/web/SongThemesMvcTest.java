package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.SongThemesConfig;
import com.songthematic.songthemes.adapter.out.jdbc.JdbcSongRepository;
import com.songthematic.songthemes.application.port.ThemeFinder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SongThemeSearcher.class})
@Import({SongThemesConfig.class})
@Tag("mvc")
@WithMockUser("fakeUser") // temporary work around
public class SongThemesMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JdbcSongRepository jdbcSongRepository;

    @MockBean
    ThemeFinder themeFinder;

    @Test
    public void getToSearchEndpointReturns200() throws Exception {
        mockMvc.perform(get("/theme-search?requestedTheme=pants"))
                .andExpect(status().is2xxSuccessful());
    }
}
