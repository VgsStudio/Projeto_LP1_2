CREATE USER 'bolsavaloreslp2'@'localhost' IDENTIFIED BY 'bolsavaloreslp2';

GRANT ALL PRIVILEGES ON * . * TO 'bolsavaloreslp2'@'localhost';

FLUSH PRIVILEGES;
SHOW DATABASES;

-- create database
CREATE DATABASE BOLSA_VALORES_LP2;
USE BOLSA_VALORES_LP2;

-- create login table
CREATE TABLE LOGIN
(
id_user SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
name VARCHAR(60) NOT NULL,
pass VARCHAR(200) NOT NULL,
PRIMARY KEY (id_user)
) ENGINE = InnoDB;

DESCRIBE LOGIN;

SELECT * FROM LOGIN;

-- create login candles
CREATE TABLE CANDLES
(
openValue DOUBLE NOT NULL,
closeValue DOUBLE NOT NULL,
highValue DOUBLE NOT NULL,
lowValue DOUBLE NOT NULL,
date VARCHAR(60) NOT NULL,
time VARCHAR(60) NOT NULL,
idCandle SMALLINT AUTO_INCREMENT,
PRIMARY KEY (idCandle)
) ENGINE = InnoDB;

DESCRIBE CANDLES;

SELECT * FROM CANDLES;

