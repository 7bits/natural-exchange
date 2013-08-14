CREATE DATABASE exchange CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE TABLE
  advertisement(
  id bigint(20) NOT NULL,
  created_date bigint(20) NOT NULL,
  is_deleted bit(1) NOT NULL,
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
  id bigint(20) NOT NULL,
  created_date bigint(20) NOT NULL,
  is_deleted bit(1) NOT NULL,
  description varchar(200) NOT NULL,
  name varchar(200) NOT NULL,
  updated_date bigint(20) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE
  searchVariant(
  id bigint(20) NOT NULL,
  created_date bigint(20) NOT NULL,
  categories varchar(200) NOT NULL,
  email varchar(200) NOT NULL,
  key_words varchar(200) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE
  subscriber(
  id bigint(20) NOT NULL,
  email varchar(200) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE
  user(
  id bigint(20) NOT NULL,
  created_date bigint(20) NOT NULL,
  updated_date bigint(20) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(255) NOT NULL,
  is_deleted bit(1) NOT NULL,
  email varchar(255) NOT NULL,
  vk_link varchar(255) NOT NULL,
  PRIMARY KEY (id)
);