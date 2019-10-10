DROP TABLE cities
IF EXISTS;
DROP SEQUENCE hibernate_sequence
IF EXISTS;

CREATE SEQUENCE hibernate_sequence
  START WITH 10
  INCREMENT BY 1;

CREATE TABLE cities (
  id          INTEGER NOT NULL PRIMARY KEY,
  name        varchar(255),
  description VARCHAR(1000),
  CONSTRAINT cities_name_idx UNIQUE (name)
);
