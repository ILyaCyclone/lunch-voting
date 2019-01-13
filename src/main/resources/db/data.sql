delete from user_roles;
delete from roles;
delete from user_votes;
delete from users;
delete from menu;
delete from restaurants;

insert into roles(id, name, description) values(100, 'ROLE_ADMIN', 'Admin can input a restaurant and it''s lunch menu of the day.');
insert into roles(id, name, description) values(200, 'ROLE_USER', 'Users can vote on which restaurant they want to have lunch at.');

insert into users(id, name, email, password) values(100, 'Admin 1', 'admin1@mail.org', '{noop}adminpass');
insert into user_roles(id_user, id_role) values (100, 100);
insert into users(id, name, email, password) values(150, 'Admin-o-user', 'admin-o-user@mail.org', '{noop}adminpass');
insert into user_roles(id_user, id_role) values (150, 100);
insert into user_roles(id_user, id_role) values (150, 200);
insert into users(id, name, email, password) values(200, 'User 1', 'user1@mail.org', '{noop}userpass');
insert into user_roles(id_user, id_role) values (200, 200);
insert into users(id, name, email, password) values(201, 'User 2', 'user2@mail.org', '{noop}userpass');
insert into user_roles(id_user, id_role) values (201, 200);
insert into users(id, name, email, password) values(202, 'User 3', 'user3@mail.org', '{noop}userpass');
insert into user_roles(id_user, id_role) values (202, 200);
insert into users(id, name, email, password) values(203, 'User 4', 'user4@mail.org', '{noop}userpass');
insert into user_roles(id_user, id_role) values (203, 200);


insert into restaurants(id, name) values(1, 'Funny Pork');
insert into restaurants(id, name) values(2, 'Gone Hungry');
insert into restaurants(id, name) values(3, 'Tables&Chairs');



insert into menu(id, id_restaurant, name, price, date) values(1, 1, 'Yesterday''s dinner', 50, '2019-01-01');
insert into menu(id, id_restaurant, name, price) values(11, 1, 'Sand Witch', 135);
insert into menu(id, id_restaurant, name, price) values(12, 1, 'When pigs fly', 99.50);

insert into menu(id, id_restaurant, name, price) values(21, 2, 'Nullified Burgers', 299.99);
insert into menu(id, id_restaurant, name, price) values(22, 2, 'Pure Water', 0.50);
insert into menu(id, id_restaurant, name, price) values(23, 2, 'Smell of pizza', 75);

insert into menu(id, id_restaurant, name, price, date) values(31, 3, 'Fish&Chips', 100, '2019-01-01');


insert into user_votes(id_restaurant, id_user) values (1, 200);
insert into user_votes(id_restaurant, id_user) values (1, 201);
insert into user_votes(id_restaurant, id_user) values (2, 202);
