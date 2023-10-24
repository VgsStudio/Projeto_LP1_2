CREATE USER 'bolsavaloreslp2'@'localhost' IDENTIFIED BY 'bolsavaloreslp2';

GRANT ALL PRIVILEGES ON * . * TO 'bolsavaloreslp2'@'localhost';

FLUSH PRIVILEGES;
SHOW DATABASES;

CREATE DATABASE BOLSA_VALORES_LP2;
USE BOLSA_VALORES_LP2;
CREATE TABLE LOGIN
(
id_user SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
name VARCHAR(60) NOT NULL,
pass VARCHAR(60) NOT NULL,
PRIMARY KEY (id_user)
) ENGINE = InnoDB;

DESCRIBE LOGIN;

SELECT * FROM LOGIN;
