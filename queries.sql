CREATE DATABASE IF NOT EXISTS testdb;
USE testdb;

CREATE TABLE IF NOT EXISTS users
(
	id INT NOT NULL AUTO_INCREMENT,
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

CREATE TABLE IF NOT EXISTS shops
(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	type VARCHAR(255),
	owner VARCHAR(255) NOT NULL,
	phone BIGINT,
	email VARCHAR(255),
	gstin VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS products
(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	image VARCHAR(255),
	description VARCHAR(255),
	price INT NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS shop_products
(
	shop_id VARCHAR(255) NOT NULL,
	product_id VARCHAR(255) NOT NULL,
	PRIMARY KEY (shop_id, product_id)
);

CREATE TABLE IF NOT EXISTS cart
(
	id INT NOT NULL AUTO_INCREMENT,
	date VARCHAR(100),
	shop_id VARCHAR(255) NOT NULL,
	customer_id VARCHAR(255) NOT NULL,
	total INT,
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cart_items
(
	cart_id INT NOT NULL,
	product_id INT NOT NULL,
	quantity INT NOT NULL,
	PRIMARY KEY (cart_id, product_id)
	-- FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE
	-- FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS orders
(
	order_id INT NOT NULL AUTO_INCREMENT,
	shop_id INT NOT NULL,
	staff_id INT NOT NULL,
	customer_id INT NOT NULL,
	order_date VARCHAR(100),
	total INT,
	mode VARCHAR(255),
	status VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_items
(
	order_id INT NOT NULL,
	product_id INT NOT NULL,
	quantity INT NOT NULL,
	PRIMARY KEY (order_id, product_id)
	-- FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
	-- FOREIGN KEY (product_id) REFERENCES products(id)
);