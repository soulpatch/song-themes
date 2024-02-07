package com.songthematic.songthemes.domain;

public class MissingSongAttribute extends RuntimeException {
    public MissingSongAttribute() {
        super();
    }

    public MissingSongAttribute(String message) {
        super(message);
    }
}
