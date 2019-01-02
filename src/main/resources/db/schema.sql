DROP TABLE IF EXISTS restaurant;
CREATE TABLE restaurant
(
  id               INT                PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name             VARCHAR            NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurant (name);


DROP TABLE IF EXISTS menu;
CREATE TABLE menu
(
  id               INT                PRIMARY KEY AUTO_INCREMENT NOT NULL,
  id_restaurant    INT                NOT NULL REFERENCES Restaurant(id),
  name             VARCHAR            NOT NULL,
  price            DECIMAL(10, 2)     NOT NULL,
  date             DATE               NOT NULL DEFAULT CURRENT_DATE()
);
ALTER TABLE menu ADD CONSTRAINT menu_dish_unique UNIQUE(id_restaurant, name, date);


DROP TABLE IF EXISTS user;
CREATE TABLE user
(
  id               INT                PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name             VARCHAR            NOT NULL,
  email            VARCHAR            NOT NULL UNIQUE
);


DROP TABLE IF EXISTS user_vote;
CREATE TABLE user_vote
(
  id_user          INT                NOT NULL REFERENCES user(id),
  id_restaurant    INT                NOT NULL REFERENCES restaurant(id),
  date             DATE               NOT NULL DEFAULT CURRENT_DATE()
);


DROP TABLE IF EXISTS role;
CREATE TABLE role
(
  id               INT                PRIMARY KEY NOT NULL,
  name             VARCHAR            NOT NULL UNIQUE,
  description      VARCHAR
);


DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
  id_user          INT                NOT NULL REFERENCES user(id),
  id_role          INT                NOT NULL REFERENCES role(id)
);

