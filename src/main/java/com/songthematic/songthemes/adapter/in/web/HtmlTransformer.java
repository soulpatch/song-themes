package com.songthematic.songthemes.adapter.in.web;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlTransformer {
    @SuppressWarnings("Convert2MethodRef")
    static String convertThemesToHtml(List<String> themes) {
        return themes.stream()
                     .map(theme -> """
                             <button class="autocomplete-suggestion" hx-post="/selected-themes" name="theme" value="%1$s">%1$s</button>"""
                             .formatted(theme))
                     .collect(Collectors.joining("\n"));
    }
}
