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
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
`body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));



