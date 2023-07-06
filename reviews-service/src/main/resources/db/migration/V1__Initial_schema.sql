CREATE TABLE REVIEWS (
	                                   ID BIGINT PRIMARY KEY,
	                                   CONTENT VARCHAR(255) NOT NULL,
	                                   IS_POSITIVE BOOLEAN NOT NULL,
                                       USER_ID BIGINT NOT NULL,
	                                   FILM_ID BIGINT NOT NULL,
	                                   USEFUL INTEGER DEFAULT 0 NOT NULL,
	                                   CONSTRAINT REVIEWS_FILM_ID_FK FOREIGN KEY (FILM_ID) REFERENCES FILMS(FILM_ID) ON DELETE CASCADE,
                                       CONSTRAINT REVIEWS_USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID) ON DELETE CASCADE
);

CREATE TABLE REVIEWS_LIKES (
                                     REVIEW_ID BIGINT NOT NULL,
                                     USER_ID BIGINT NOT NULL,
                                     IS_LIKE BOOLEAN NOT NULL,
                                     CONSTRAINT REVIEWS_LIKES_REVIEW_ID_USER_ID_PK PRIMARY KEY (REVIEW_ID,USER_ID,IS_LIKE) ,
                                     CONSTRAINT REVIEWS_LIKES_REVIEW_ID_FK FOREIGN KEY (REVIEW_ID) REFERENCES REVIEWS(ID) ON DELETE CASCADE,
);

CREATE INDEX REVIEWS_LIKES_REVIEW_ID_FK_INDEX ON REVIEWS_LIKES(REVIEW_ID);
CREATE INDEX REVIEWS_LIKES_USER_ID_FK_INDEX ON REVIEWS_LIKES(USER_ID);