DROP TABLE `student`;
CREATE TABLE `student`(
  `sid` CHAR(9) NOT NULL,
  `sname` CHAR(30) NOT NULL,
  `gender` CHAR(2) NOT NULL,
  `department` CHAR(40) NOT NULL,
  PRIMARY KEY (sid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE `dorm`;
CREATE TABLE `dorm` (
  `dname` CHAR(20) NOT NULL,
  `fee` DOUBLE NOT NULL,
  `phone` CHAR(20) NOT NULL,
  `campus` CHAR(20) NOT NULL,
  PRIMARY KEY (dname)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE `location`;
CREATE TABLE `location` (
  `department` CHAR(40) NOT NULL,
  `gender` CHAR(2) NOT NULL,
  `dname` CHAR(20) NOT NULL,
  PRIMARY KEY (department, gender)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SELECT * FROM `student` S WHERE S.sname='王小星';

SELECT L1.department FROM `location` L1 WHERE L1.dname IN (
  SELECT L.dname FROM `location` L WHERE L.gender='男' AND L.department='中美文化研究中心'
);

UPDATE `dorm` SET fee=1200 WHERE dname='陶园1舍';

SELECT * FROM `location` WHERE department='软件学院' AND gender='男'

