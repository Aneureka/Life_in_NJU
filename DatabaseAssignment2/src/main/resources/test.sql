# # DROP TABLE IF EXISTS `user`;
# CREATE TABLE `user`(
#   `uid` INT(11) NOT NULL,
#   `name` CHAR(30) NOT NULL,
#   `phone` CHAR(20) NOT NULL,
#   `balance` DOUBLE NOT NULL,
#   `address` VARCHAR(50) NOT NULL,
#   PRIMARY KEY (uid)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
#
# # DROP TABLE IF EXISTS `bike`;
# CREATE TABLE `bike`(
#   `bid` INT(11) NOT NULL,
#   PRIMARY KEY (bid)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
#
# # DROP TABLE IF EXISTS `record`;
# CREATE TABLE `record`(
#   `uid` INT(11) NOT NULL,
#   `bid` INT(11) NOT NULL,
#   `start_point` CHAR(40) NOT NULL,
#   `start_time` DATETIME NOT NULL,
#   `end_point` CHAR(40) NOT NULL,
#   `end_time` DATETIME NOT NULL,
#   `cost` DOUBLE NOT NULL,
#   PRIMARY KEY (uid, bid, start_time)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
#
#
#
#
CREATE TABLE `user_location` (
  `uid` INT(11) NOT NULL,
  `location` VARCHAR(50) NOT NULL,
  `frequency` INT(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_location` (
  SELECT
    uc.uid,
    uc.end_point,
    max(uc.num)
  FROM
    (SELECT
       R.uid,
       R.end_point,
       count(R.end_point) AS num
     FROM `record` R
     WHERE time(R.end_time) BETWEEN '18:00' AND '24:00'
     GROUP BY R.uid, R.end_point) uc
  GROUP BY uc.uid, uc.end_point
);

SELECT R.bid, count(TIMESTAMPDIFF(HOUR, R.start_time, R.end_time)) FROM `record` R
WHERE DATEDIFF(R.end_time, CURTIME()) <= 30
GROUP BY R.bid;

CREATE TABLE `bike_use` (
  `bid` INT(11) NOT NULL,
  `end_point` INT(11) NOT NULL,
  PRIMARY KEY (bid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `bike_use`(
SELECT R1.bid, R1.end_point FROM `record` R1 WHERE EXISTS
  (SELECT * FROM
      (SELECT R2.bid, count(TIMESTAMPDIFF(HOUR, R2.start_time, R2.end_time)) AS use_time FROM `record` R2
      WHERE DATEDIFF(R2.end_time, CURTIME()) <= 30
      GROUP BY R2.bid
      HAVING use_time > 200) u WHERE u.bid=R1.bid) AND NOT EXISTS(
    SELECT R3.bid, R3.end_time FROM `record` R3 WHERE R3.bid=R1.bid AND R3.end_time>R1.end_time)
);
