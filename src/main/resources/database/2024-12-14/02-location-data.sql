--liquibase formatted sql
--changeset weatherService:2

INSERT INTO location (id, city_name, country_name) VALUES
   (1,'Jastarnia', 'Poland'),
   (2,'Bridgetown', 'Barbados'),
   (3,'Fortaleza', 'Brazil'),
   (4,'Pissouri', 'Cyprus'),
   (5,'Le Morne', 'Mauritius');