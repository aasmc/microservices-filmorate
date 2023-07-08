CREATE TABLE IF NOT EXISTS LIKES (
                        FILM_ID BIGINT NOT NULL,
                        USER_ID BIGINT NOT NULL,
                        MARK REAL NOT NULL,
                        TIMESTAMP BIGINT NOT NULL,
                        CONSTRAINT LIKES_FILM_ID_USER_ID_PK PRIMARY KEY (FILM_ID,USER_ID)
);