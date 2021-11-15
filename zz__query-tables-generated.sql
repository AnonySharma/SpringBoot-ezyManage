CREATE DATABASE 'testdb';

CREATE TABLE `users` (
  `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) UNIQUE NOT NULL,
  `password` VARCHAR(1000) NOT NULL,
  `role` VARCHAR(100),
  `isadmin` BOOLEAN
);

CREATE TABLE `profile` (
  `username` VARCHAR(255) PRIMARY KEY NOT NULL,
  `firstname` VARCHAR(20),
  `middlename` VARCHAR(20),
  `lastname` VARCHAR(20),
  `phone` BIGINT,
  `gender` CHAR(1),
  `dob` TIMESTAMP,
  `email` VARCHAR(255),
  `address` VARCHAR(255),
  `aadhaar` VARCHAR(50)
);

CREATE TABLE `shops` (
  `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `type` VARCHAR(255),
  `owner` VARCHAR(255) NOT NULL,
  `phone` BIGINT,
  `email` VARCHAR(255),
  `gstin` VARCHAR(255)
);

CREATE TABLE `shop_staffs` (
  `staff_id` INT NOT NULL,
  `staff_name` VARCHAR(255) NOT NULL,
  `shop_id` INT NOT NULL,
  `date_of_joining` TIMESTAMP,
  `designation` VARCHAR(255),
  `salary` INT,
  PRIMARY KEY (`staff_id`, `shop_id`)
);

CREATE TABLE `products` (
  `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `image` VARCHAR(255),
  `description` VARCHAR(255),
  `price` INT NOT NULL
);

CREATE TABLE `shop_products` (
  `shop_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  PRIMARY KEY (`shop_id`, `product_id`)
);

CREATE TABLE `cart` (
  `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `date` TIMESTAMP,
  `shop_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `total` INT
);

CREATE TABLE `cart_items` (
  `cart_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`cart_id`, `product_id`)
);

CREATE TABLE `orders` (
  `order_id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(255) UNIQUE NOT NULL,
  `shop_id` INT NOT NULL,
  `staff_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `order_date` TIMESTAMP,
  `total` INT,
  `mode` VARCHAR(255),
  `status` VARCHAR(255)
);

CREATE TABLE `order_items` (
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`order_id`, `product_id`)
);

ALTER TABLE `profile` ADD FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE;

ALTER TABLE `shops` ADD FOREIGN KEY (`owner`) REFERENCES `users` (`username`) ON DELETE CASCADE;

ALTER TABLE `shop_staffs` ADD FOREIGN KEY (`staff_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `shop_staffs` ADD FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`) ON DELETE CASCADE;

ALTER TABLE `shop_products` ADD FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`) ON DELETE CASCADE;

ALTER TABLE `shop_products` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE;

ALTER TABLE `cart` ADD FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`) ON DELETE CASCADE;

ALTER TABLE `cart` ADD FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `cart_items` ADD FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE;

ALTER TABLE `cart_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE;

ALTER TABLE `orders` ADD FOREIGN KEY (`shop_id`) REFERENCES `shops` (`id`) ON DELETE CASCADE;

ALTER TABLE `orders` ADD FOREIGN KEY (`staff_id`) REFERENCES `shop_staffs` (`staff_id`) ON DELETE CASCADE;

ALTER TABLE `orders` ADD FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `order_items` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE;

ALTER TABLE `order_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE;

