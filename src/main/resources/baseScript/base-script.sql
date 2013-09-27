CREATE DATABASE exchange CHARACTER SET utf8 COLLATE utf8_general_ci;
USE exchange;
CREATE TABLE
  advertisement(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_date bigint(20) NOT NULL,
  is_deleted bit(1) NOT NULL,
  is_new bit(1) NOT NULL,
  photo_file varchar(255) NOT NULL,
  text text NOT NULL,
  title varchar(200) NOT NULL,
  updated_date bigint(20) NOT NULL,
  category_id bigint(20),
  user_id bigint(20),
  PRIMARY KEY (id)
);
CREATE TABLE
  Category(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_date bigint(20) NOT NULL,
  is_deleted bit(1) NOT NULL,
  description varchar(200) NOT NULL,
  name varchar(200) NOT NULL,
  updated_date bigint(20) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE
  searchVariant(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_date bigint(20) NOT NULL,
  categories varchar(200) NOT NULL,
  email varchar(200) NOT NULL,
  key_words varchar(200) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE
  subscriber(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  email varchar(200) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE
  user(
  id bigint(20) NOT NULL AUTO_INCREMENT,
  created_date bigint(20) NOT NULL,
  updated_date bigint(20) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) NOT NULL,
  is_deleted bit(1) NOT NULL,
  email varchar(255) NOT NULL,
  activation_code varchar(255),
  activation_date bigint(20),
  vk_link varchar(255),
  PRIMARY KEY (id)
);