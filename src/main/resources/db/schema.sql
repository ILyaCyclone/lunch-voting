DROP TABLE IF EXISTS restaurants;
CREATE TABLE restaurants
(
  id               INT                PRIMARY KEY AUTO_INCREMENT NOT NULL,
  name             VARCHAR            NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurants(name);


DROP TABLE IF EXISTS menu;
CREATE TABLE menu
(
  id               INT                PRIMARY KEY AUTO_INCREMENT NOT NULL,
  id_restaurant    INT                NOT NULL REFERENCES restaurants(id),
  name             VARCHAR            NOT NULL,
  price            DECIMAL(10, 2)     NOT NULL,
  date             DATE               NOT NULL DEFAULT CURRENT_DATE(),
  CONSTRAINT menu_dish_unique UNIQUE(id_restaurant, name, date)
);


DROP TABLE IF EXISTS users;
CREATE TABLE users
(
  id               INT                PRIMARY KEY AUTO_INCREMENT NOT NULL,
  email            VARCHAR            NOT NULL UNIQUE,
  name             VARCHAR            NOT NULL,
  password         VARCHAR            NOT NULL
);


DROP TABLE IF EXISTS user_votes;
CREATE TABLE user_votes
(
  id               INT                PRIMARY KEY AUTO_INCREMENT NOT NULL,
  id_user          INT                NOT NULL REFERENCES users(id),
  id_restaurant    INT                NOT NULL REFERENCES restaurants(id),
  date             DATE               NOT NULL DEFAULT CURRENT_DATE()
);


DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
  id               INT                PRIMARY KEY NOT NULL,
  name             VARCHAR            NOT NULL UNIQUE,
  description      VARCHAR
);


DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles
(
  id_user          INT                NOT NULL REFERENCES users(id),
  id_role          INT                NOT NULL REFERENCES roles(id)
);

