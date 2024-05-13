insert into hotel (name, description, city, address, distance_from_center, rating, number_of_ratings) values ('Sauer and Sons', 'Quisque id justo sit amet sapien dignissim vestibulum.', 'Sinegor''ye', '2575 Spohn Alley', 10.7, 3.09, 21);
insert into hotel (name, description, city, address, distance_from_center, rating, number_of_ratings) values ('Walter LLC', 'Sed accumsan felis.', 'Soukkouma', '977 Holy Cross Street', 40.28, 3.24, 18);
insert into hotel (name, description, city, address, distance_from_center, rating, number_of_ratings) values ('Lubowitz LLC', 'Nunc nisl.', 'Lindavista', '6 Meadow Vale Plaza', 33.62, 2.32, 35);
insert into hotel (name, description, city, address, distance_from_center, rating, number_of_ratings) values ('Bogisich-Bayer', 'Ut at dolor quis odio consequat varius.', 'Xike', '6277 Oneill Place', 13.9, 2.18, 5);
insert into hotel (name, description, city, address, distance_from_center, rating, number_of_ratings) values ('Hintz, Zemlak and Walsh', 'Duis consequat dui nec nisi volutpat eleifend.', 'Jiyukou', '5 Merrick Avenue', 62.0, 4.9, 168);

insert into room (description, number, price, max_people, hotel_id) values ('Pellentesque ultrices mattis odio. Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla. Sed vel enim sit amet nunc viverra dapibus.', 79, 154.62, 1, 3);
insert into room (description, number, price, max_people, hotel_id) values ('Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam. Nam tristique tortor eu pede.', 38, 6321.71, 10, 5);
insert into room (description, number, price, max_people, hotel_id) values ('Quisque porta volutpat erat. Quisque erat eros, viverra eget, congue eget, semper rutrum, nulla.', 36, 4599.64, 8, 3);
insert into room (description, number, price, max_people, hotel_id) values ('Suspendisse potenti.', 23, 2908.3, 7, 1);
insert into room (description, number, price, max_people, hotel_id) values ('Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Duis faucibus accumsan odio. Curabitur convallis. Duis consequat dui nec nisi volutpat eleifend. Donec ut dolor.', 59, 8933.39, 6, 4);

insert into unavailable_dates (room_id, from_date, to_date) values (5, '2023-10-09', '2024-12-20');
insert into unavailable_dates (room_id, from_date, to_date) values (4, '2023-03-27', '2024-05-26');
insert into unavailable_dates (room_id, from_date, to_date) values (4, '2023-10-03', '2024-03-02');
insert into unavailable_dates (room_id, from_date, to_date) values (4, '2023-09-17', '2024-03-25');
insert into unavailable_dates (room_id, from_date, to_date) values (3, '2023-10-17', '2024-03-11');

insert into users (username, email, password, role) values ('admin', 'shanfrey0@google.ru', 'admin', 'ADMIN');
insert into users (username, email, password, role) values ('user', 'tgoodread1@spiegel.de', 'user', 'USER');

insert into booking (room_id, user_id, from_date, to_date) values (5, 2, '2024-05-10', '2024-09-19');
insert into booking (room_id, user_id, from_date, to_date) values (3, 2, '2024-03-09', '2024-11-04');
insert into booking (room_id, user_id, from_date, to_date) values (3, 1, '2024-05-28', '2024-12-24');
insert into booking (room_id, user_id, from_date, to_date) values (1, 1, '2024-03-12', '2024-12-07');
insert into booking (room_id, user_id, from_date, to_date) values (2, 1, '2024-05-29', '2024-07-03');
