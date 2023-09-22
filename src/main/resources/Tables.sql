drop table flight if exists;
create table flight (flight_id int primary key, departure_city varchar(255), arrival_city varchar(255));
insert into flight (flight_id, departure_city, arrival_city) values
(1111, 'tampa', 'new york city'),
(2222, 'new york city', 'philadelphia'),
(3333, 'philadelphia', 'denver');