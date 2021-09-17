INSERT INTO Clients (clientName, clientType)
VALUES ('Apple', 'juridical'),
       ('Amazon', 'juridical'),
       ('Facebook', 'juridical'),
       ('Yandex', 'juridical'),
       ('John Lennon', 'natural'),
       ('Paul McCartney', 'natural'),
       ('George Harrison', 'natural'),
       ('Ringo Starr ', 'natural'),
       ('Bob Dylan ', 'natural'),
       ('Elvis Presley ', 'natural'),
       ('Gazproom', 'juridical'),
       ('Tesla', 'juridical');

INSERT INTO CreditTypes (creditname, creditlimit, creditperiod, interestrate, finefrequency)
VALUES ('low', 100000, 365, 31.3, 'week'),
       ('middle', 900000, 3650, 9.1, 'month'),
       ('high', 9900000, 3650, 0.5, 'year'),
       ('premium', 1700000, 3650, 12, 'month'),
       ('ultra', 1820000, 7300, 6, 'year');

INSERT INTO Departments(address)
VALUES ('Red Square 1B'),
       ('MSU, 19A'),
       ('HSE, 7C'),
       ('Moscow-Sity, 12'),
       ('Gagarin Street, 8'),
       ('Lenin street, 11');

INSERT INTO accounts(clientID, departmentID, credittypeID, balance, lastchange)
VALUES (1, 1, 1, 10800, '+ 3 rub'),
       (1, 2, 1, 0, '+ 234 rub'),
       (2, 1, 2, 670, '+ 10000 rub'),
       (2, 2, 3, -400, '- 500 rub'),
       (2, 3, 3, 97600, '- 0 rub'),
       (3, 3, 1, 1000, '+ 0 rub'),
       (1, 1, 1, 10800, '+ 0 rub'),
       (6, 3, 4, 0, '+ 340 rub'),
       (2, 4, 5, 60, '+ 300 rub'),
       (2, 1, 5, -400, '- 400 rub'),
       (4, 3, 2, 900, '- 2000 rub');
