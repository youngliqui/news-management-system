--liquibase formatted sql

--changeset youngliqui:1
--comment creating entity tables

CREATE SEQUENCE news_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE news
(
    id    BIGINT    DEFAULT NEXTVAL('news_id_seq') PRIMARY KEY,
    time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    title VARCHAR(255) NOT NULL,
    text  TEXT         NOT NULL
);

CREATE SEQUENCE comments_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE comments
(
    id       BIGINT    DEFAULT NEXTVAL('comments_id_seq') PRIMARY KEY,
    time     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username VARCHAR(100) NOT NULL,
    text     TEXT         NOT NULL,
    news_id  BIGINT,
    FOREIGN KEY (news_id) REFERENCES news (id) ON DELETE CASCADE
);