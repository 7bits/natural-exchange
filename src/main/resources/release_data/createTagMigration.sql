CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `advertisement_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1BF9AC22800F4` (`advertisement_id`),
  CONSTRAINT `FK1BF9AC22800F4` FOREIGN KEY (`advertisement_id`) REFERENCES `advertisement` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;