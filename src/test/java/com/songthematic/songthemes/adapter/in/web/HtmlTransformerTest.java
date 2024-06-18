package com.songthematic.songthemes.adapter.in.web;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlTransformerTest {

    @Test
    void convertMultipleThemesToHtml() throws Exception {
        List<String> themes = List.of("Cats", "Dogs");

        String html = HtmlTransformer.convertThemesToHtml(themes);

        assertThat(html)
                .isEqualTo("<button>Cats</button>\n<button>Dogs</button>");
    }
}