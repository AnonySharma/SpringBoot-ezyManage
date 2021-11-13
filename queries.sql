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
	dob TIMESTAMP,
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

CREATE TABLE IF NOT EXISTS shop_staffs
(
	staff_id INT NOT NULL,
	staff_name VARCHAR(255) NOT NULL,
	shop_id INT NOT NULL,
	date_of_joining TIMESTAMP,
	designation VARCHAR(255),
	salary INT,
	PRIMARY KEY (staff_id, shop_id),
	FOREIGN KEY (staff_id) REFERENCES users(id),
	FOREIGN KEY (shop_id) REFERENCES shops(id)
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
	date TIMESTAMP,
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
	token VARCHAR(255) UNIQUE NOT NULL,
	shop_id INT NOT NULL,
	staff_id INT NOT NULL,
	customer_id INT NOT NULL,
	order_date TIMESTAMP,
	total INT,
	mode VARCHAR(255),
	status VARCHAR(255),
	PRIMARY KEY (order_id)
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

-- ----------------------------------- Populate products table -------------------------------------
-- INSERT INTO products(name, image, description, price) VALUES("Rice", "https://d3pc1xvrcw35tl.cloudfront.net/ln/images/686x514/rice-news-20180262183_202106208254.jpg", "Chaaawal", 10);
-- INSERT INTO products(name, image, description, price) VALUES("Sugar", "https://www.kinder.com/in/sites/kinder_in/files/documents/16871047/22803799/kinder-39-Sugar-final-header.jpg?t=1623490499", "Cheeni", 45);
-- INSERT INTO products(name, image, description, price) VALUES("Salt", "https://post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/02/322745_1100-732x549.jpg", "Namak", 12);
-- INSERT INTO products(name, image, description, price) VALUES("Juggary", "https://static.toiimg.com/photo/msid-72056635/72056635.jpg", "Gud", 20);
-- INSERT INTO products(name, image, description, price) VALUES("Flour", "https://www.unlockfood.ca/EatRightOntario/media/Website-images-resized/All-about-grain-flours-resized.jpg", "Aata", 35);
-- -------------------------------------------------------------------------------------------------