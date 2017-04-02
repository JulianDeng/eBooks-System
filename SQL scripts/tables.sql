DROP TABLE if exists POItem;
DROP TABLE if exists PO;
DROP TABLE if exists VisitEvent;
DROP TABLE if exists Rating;
DROP TABLE if exists Review;
DROP TABLE if exists Book;
DROP TABLE if exists Address;
DROP TABLE if exists Customer;
DROP TABLE if exists Administrator;


CREATE TABLE Book (
	bid VARCHAR(20) NOT NULL,
	title VARCHAR(60) NOT NULL,
	price INT NOT NULL,
	category ENUM('Science','Fiction','Engineering') NOT NULL,
	PRIMARY KEY(bid)
);

CREATE TABLE Customer (
	uid INT NOT NULL AUTO_INCREMENT,
	username varchar(20) NOT NULL,
	password varchar(20) NOT NULL,
	lname varchar(20) NOT NULL,
	fname varchar(20) NOT NULL,
	PRIMARY KEY(uid)
);

CREATE TABLE Address (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	street VARCHAR(100) NOT NULL,
	province VARCHAR(20) NOT NULL,
	country VARCHAR(20) NOT NULL,
	zip VARCHAR(20) NOT NULL,
	phone VARCHAR(20),
	PRIMARY KEY(id)
);

CREATE TABLE PO (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	status ENUM('ORDERED','PROCESSED','DENIED') NOT NULL,
	address INT UNSIGNED NOT NULL,
	uid INT,
	PRIMARY KEY(id),
	FOREIGN KEY(address) REFERENCES Address(id) ON DELETE CASCADE,
	FOREIGN KEY(uid) REFERENCES Customer(uid) ON DELETE CASCADE
);

CREATE TABLE POItem (
	id INT UNSIGNED NOT NULL,
	bid VARCHAR(20) NOT NULL,
	price INT UNSIGNED NOT NULL,
	quantity INT,
	PRIMARY KEY(id,bid),
	FOREIGN KEY(id) REFERENCES PO(id) ON DELETE CASCADE,
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);

CREATE TABLE VisitEvent (
	day varchar(8) NOT NULL,
	bid varchar(20) not null REFERENCES Book.bid,
	eventtype ENUM('VIEW','CART','PURCHASE') NOT NULL,
	FOREIGN KEY(bid) REFERENCES Book(bid)
);

CREATE TABLE Rating (
	bid varchar(20) not null,
	score TINYINT NOT NULL,
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);

CREATE TABLE Review (
	bid varchar(20) not null,
	comment varchar(100) NOT NULL,
	date varchar(8) NOT NULL,
	FOREIGN KEY(bid) REFERENCES Book(bid) ON DELETE CASCADE
);

CREATE TABLE Administrator (
	username varchar(20) NOT NULL,
	password varchar(20) NOT NULL
);
