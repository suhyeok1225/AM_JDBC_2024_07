DROP DATABASE IF EXISTS `AM_JDBC_2024_07`;
CREATE DATABASE `AM_JDBC_2024_07`;
USE `AM_JDBC_2024_07`;
CREATE TABLE article(
                        id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                        regDate DATETIME NOT NULL,
                        updateDate DATETIME NOT NULL,
                        title CHAR(100) NOT NULL,
                        `body` TEXT NOT NULL
);

CREATE TABLE `member`(
                         id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         regDate DATETIME NOT NULL,
                         updateDate DATETIME NOT NULL,
                         loginId CHAR(30) NOT NULL,
                         loginPw CHAR(200) NOT NULL,
                         `name` CHAR(100) NOT NULL
);







