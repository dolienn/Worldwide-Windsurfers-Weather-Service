--liquibase formatted sql
--changeset weatherService:1

CREATE TABLE location (
    id INT NOT NULL PRIMARY KEY,
    city_name VARCHAR(255) NOT NULL,
    country_name VARCHAR(255) NOT NULL
);