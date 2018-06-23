# 客户信息
CREATE TABLE `customers` (
  `cid` INT(11) NOT NULL,
  `password` CHAR(20) NOT NULL,
  `name` CHAR(20) NOT NULL,
  PRIMARY KEY (cid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 订单信息
CREATE TABLE `orders` (
  `oid` INT(11) AUTO_INCREMENT NOT NULL,
  `cid` INT(11) NOT NULL,
  `pid` INT(11) NOT NULL,
  `quantity` INT(6) NOT NULL,
  `total_price` DOUBLE NOT NULL,
  `created_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (oid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop TABLE `orders`;

DROP TABLE `products`;

# 商品信息
CREATE TABLE `products` (
  `pid` INT(11) AUTO_INCREMENT NOT NULL,
  `name` CHAR(40) NOT NULL,
  `price` DOUBLE NOT NULL,
  `quantity` INT(6) NULL DEFAULT 0,
  PRIMARY KEY (pid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
