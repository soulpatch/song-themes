package com.songthematic.songthemes.adapter.in.web;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlTransformer {
    static String convertThemesToHtml(List<String> themes) {
        return themes.stream()
                     .collect(Collectors.joining("</button>\n<button>", "<button>", "</button>"));
    }
}
