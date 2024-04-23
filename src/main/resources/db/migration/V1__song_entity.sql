CREATE TABLE songs
(
    id            SERIAL PRIMARY KEY NOT NULL,
    artist        TEXT               NOT NULL,
    song_title    TEXT               NOT NULL,
    release_title TEXT               NOT NULL,
    release_type  TEXT               NOT NULL,
    themes        TEXT[] NOT NULL
);
