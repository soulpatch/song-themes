package com.songthematic.songthemes.adapter.in.web;

import com.songthematic.songthemes.adapter.out.jdbc.SongJdbcRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Tag("mvc")
@WithMockUser("fakeUser") // temporary work around
public class SongThemesMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SongJdbcRepository songJdbcRepository;

    @Test
    public void getToSearchEndpointReturns200() throws Exception {
        mockMvc.perform(get("/theme-search?requestedTheme=pants"))
                .andExpect(status().is2xxSuccessful());
    }
}
