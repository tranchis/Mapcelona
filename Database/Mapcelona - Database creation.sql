CREATE DATABASE `sergioal_mapcelona` CHARACTER SET utf8;
USE sergioal_mapcelona;

CREATE TABLE IF NOT EXISTS city
(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT PK_city PRIMARY KEY (id)
) TYPE = INNODB;

CREATE TABLE IF NOT EXISTS district
(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	city_id INT UNSIGNED COMMENT 'Foreign key to a city',
	name VARCHAR(255) NOT NULL,
	CONSTRAINT PK_district PRIMARY KEY (id),
	CONSTRAINT FK_district_0 FOREIGN KEY (city_id) REFERENCES city (id)
) TYPE = INNODB;

CREATE TABLE IF NOT EXISTS neighbourhood
(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	district_id INT UNSIGNED COMMENT 'Foreign key to a district',
	name VARCHAR(255) NOT NULL,
	CONSTRAINT PK_neighbourhood PRIMARY KEY (id),
	CONSTRAINT FK_neighbourhood_0 FOREIGN KEY (district_id) REFERENCES district (id)
) TYPE = INNODB;

CREATE TABLE IF NOT EXISTS dataclass
(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR (255) NOT NULL,
	CONSTRAINT PK_dataclass PRIMARY KEY (id)
) TYPE = INNODB;

CREATE TABLE IF NOT EXISTS datasubclass
(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	parent_id INT UNSIGNED NOT NULL COMMENT 'Foreign keys to set a hierarchy between dataclasses',
	child_id INT UNSIGNED NOT NULL,
	CONSTRAINT PK_datasubclass PRIMARY KEY (id),
	CONSTRAINT UNIQUE_datasubclass UNIQUE (parent_id, child_id),
	CONSTRAINT FK_datasubclass_0 FOREIGN KEY (parent_id) REFERENCES dataclass (id),
	CONSTRAINT FK_datasubclass_1 FOREIGN KEY (child_id) REFERENCES dataclass (id)
) TYPE = INNODB;

CREATE TABLE IF NOT EXISTS district_value
(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	district_id INT UNSIGNED NOT NULL,
	dataclass_id INT UNSIGNED NOT NULL,
	_value FLOAT COMMENT 'Specific value of a dataclass in a district',
	CONSTRAINT PK_district_value PRIMARY KEY (id),
	CONSTRAINT UNIQUE_district_value UNIQUE (district_id, dataclass_id),
	CONSTRAINT FK_district_value_0 FOREIGN KEY (district_id) REFERENCES district (id),
	CONSTRAINT FK_district_value_1 FOREIGN KEY (dataclass_id) REFERENCES dataclass (id)
) TYPE = INNODB;

CREATE TABLE IF NOT EXISTS neighbourhood_value
(
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	neighbourhood_id INT UNSIGNED NOT NULL,
	dataclass_id INT UNSIGNED NOT NULL,
	_value FLOAT COMMENT 'Specific value of a dataclass in a neighbourhood',
	CONSTRAINT PK_neighbourhood_value PRIMARY KEY (id),
	CONSTRAINT UNIQUE_neighbourhood_value UNIQUE (neighbourhood_id, dataclass_id),
	CONSTRAINT FK_neighbourhood_value_0 FOREIGN KEY (neighbourhood_id) REFERENCES neighbourhood (id),
	CONSTRAINT FK_neighbourhood_value_1 FOREIGN KEY (dataclass_id) REFERENCES dataclass (id)
) TYPE = INNODB;