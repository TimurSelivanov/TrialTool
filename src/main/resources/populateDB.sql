insert into customer(name, phone)
values ('Tom', 123),
       ('Ann', 456),
       ('Bob', 789);

insert into tool(brand, type)
values ('Bosch', 'Table saw'),
       ('Makita', 'Chain saw'),
       ('Dewalt', 'Miter saw');

update tool set customer_id = 1, taken_on='2023-02-15 08:00:00' WHERE id=1;