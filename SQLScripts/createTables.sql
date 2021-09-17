DROP TABLE IF EXISTS Clients, Departments, CreditTypes, Accounts;

CREATE TABLE Clients (
    clientID serial,
    clientName varchar(128),
    clientType varchar(128),
    PRIMARY KEY (clientID)
);


CREATE TABLE Departments(
    departmentID serial,
    address varchar(1024),
    PRIMARY KEY (departmentID)
);


CREATE TABLE CreditTypes(
    creditTypeID serial,
    creditName varchar(128),
    creditLimit integer,
    --  creditPeriod is the number of days
    creditPeriod integer,
    --  percent per year
    interestRate float,
    fineFrequency varchar(128),
    PRIMARY KEY (creditTypeID)
);


CREATE TABLE Accounts(
    accountID serial,
    clientID integer REFERENCES Clients,
    departmentID integer REFERENCES Departments,
    creditTypeID integer REFERENCES CreditTypes,
    balance integer,
    lastChange varchar(128),
    PRIMARY KEY (accountID)
);
