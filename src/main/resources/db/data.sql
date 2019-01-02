insert into role(id, name, description) values(100, 'ROLE_ADMIN', 'Admin can input a restaurant and it''s lunch menu of the day.');
insert into role(id, name, description) values(200, 'ROLE_USER', 'Users can vote on which restaurant they want to have lunch at.');

insert into user(id, name, email) values(100, 'Admin 1', 'admin1@mail.org');
insert into user_role(id_user, id_role) values (100, 100);
insert into user(id, name, email) values(150, 'Admin-o-user', 'admin-o-user@mail.org');
insert into user_role(id_user, id_role) values (150, 100);
insert into user_role(id_user, id_role) values (150, 200);
insert into user(id, name, email) values(200, 'User 1', 'user1@mail.org');
insert into user_role(id_user, id_role) values (200, 200);
insert into user(id, name, email) values(201, 'User 2', 'user2@mail.org');
insert into user_role(id_user, id_role) values (201, 200);
insert into user(id, name, email) values(202, 'User 3', 'user3@mail.org');
insert into user_role(id_user, id_role) values (202, 200);


insert into restaurant(id, name) values(1, 'Funny Pork');
insert into restaurant(id, name) values(2, 'Tables&Chairs');
insert into restaurant(id, name) values(3, 'Gone Hungry');



insert into menu(id_restaurant, name, price, date) values(1, 'Yesterday''s dinner', 50, '2019-01-01');
insert into menu(id_restaurant, name, price) values(1, 'When pigs fly', 99.50);
insert into menu(id_restaurant, name, price) values(1, 'Sand Witch', 135);

insert into menu(id_restaurant, name, price) values(3, 'Nullified Burgers', 299.99);
insert into menu(id_restaurant, name, price) values(3, 'Smell of pizza', 75);
insert into menu(id_restaurant, name, price) values(3, 'Pure Water', 0.50);
