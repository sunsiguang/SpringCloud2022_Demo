CREATE TABLE `User1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `pwd` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

drop table `user`
CREATE TABLE `user` (
   `id` int(11)  DEFAULT NULL  AUTO_INCREMENT,
   `username` varchar(32) DEFAULT NULL,
  `firstname` varchar(32) DEFAULT NULL,
  `lastname` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  AUTO_INCREMENT=1   DEFAULT CHARSET=utf8
