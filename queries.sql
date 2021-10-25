CREATE TABLE IF NOT EXISTS user
(
	id VARCHAR(255) UNIQUE NOT NULL,
	username VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(1000) NOT NULL,
	role VARCHAR(100),
	isadmin BOOLEAN,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS profile
(
	username VARCHAR(255) UNIQUE NOT NULL,
	firstname VARCHAR(20),
	middlename VARCHAR(20),
	lastname VARCHAR(20),
	phone BIGINT,
	gender CHAR(1),
	dob DATE,
	email VARCHAR(255),
	address VARCHAR(255),
	aadhaar VARCHAR(50),
	PRIMARY KEY (username)
);

