package com.songthematic.songthemes.application;

public class NotEnoughColumns extends RuntimeException {
    public NotEnoughColumns() {
        super();
    }

    public NotEnoughColumns(String message) {
        super(message);
    }
}
