package com.songthematic.songthemes;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Tag("io")
public class SongThemesMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getToSearchEndpointReturns200() throws Exception {
        mockMvc.perform(get("/theme-search?requestedTheme=pants"))
                .andExpect(status().is2xxSuccessful());
    }
}
