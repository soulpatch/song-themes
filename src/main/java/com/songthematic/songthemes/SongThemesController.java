package com.songthematic.songthemes;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongThemesController {
    @GetMapping("/")
    public String welcome() {
        return """
                <!doctype html>
                <html lang=en>
                <head>
                <meta charset=utf-8>
                <title>Song Thematic</title>
                </head>
                <body>
                <p>Coming soon</p>
                </body>
                </html>""";
    }
}
