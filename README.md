# ezyManage

<img src="https://wakatime.com/badge/user/6f0a3756-f42b-4b2c-b51e-3af523e8c304/project/d21c1e55-75e1-4554-a93c-b5b182e8054d.svg" alt="wakatime">

ezyManage is a web application backed by Spring Boot and MySQL, that helps you manage your shops including products, inventory, staffs, customers, orders and payments.

![Website Landing Page](https://github.com/AnonySharma/SpringBoot-ezyManage/blob/master/images/home.jpg?raw=true)

# Relational Schema

![Relational Schema](https://github.com/AnonySharma/SpringBoot-ezyManage/blob/master/images/schema.png?raw=true)

## TODO:

-   Write dummy data for the database in queries.sql.
-   Edit products
-   Update profile

## Bugs:

-   Any owner or staff can open any shop from link

## Roles we have

-   **ROLE_USER** - User with limited access to past orders, stores they visit, etc.
-   **ROLE_ADMIN** - User with full access to all the shops, products and users.
-   **ROLE_OWNER** - User with full access to all the shops under him, and limited access to its customers and staff.
-   **ROLE_STAFF** - User with partial access to shop they work in.

| Role       | Is created by                           |
| ---------- | --------------------------------------- |
| ROLE_ADMIN | Manually entering value in the database |
| ROLE_OWNER | Admin, By request                       |
| ROLE_STAFF | Owner                                   |

_Note:_

-   A new registrant is initially a USER, though they can opt to start a store and get an additional role of an Owner.
-   The owner has the access to add its staff but that staff must already be an user.

Posssible combinations:

-   ROLE_USER, ROLE_ADMIN
-   ROLE_USER, ROLE_OWNER, ROLE_ADMIN
-   ROLE_USER, ROLE_OWNER
-   ROLE_USER, ROLE_STAFF

# Usage guide

## To create a new admins

-   Register a user
-   Go to database, and append " ROLE_ADMIN" to its role, and make is_admin = 1

### Test admin

**Username:** admin <br>
**Password:** admin

## To start a new store

-   Register a user
-   Request to get owner role, open admin panel, and accept its request

# Initial Commands:

-   git init
-   git add .
-   git commit -m "test"
-   heroku create (optional)
-   heroku git:remote -a ezy-manage
-   git push heroku master (optional)
-   git push origin master
-   heroku logs --tail

# Testing Commands:

-   git add .
-   git commit -m "test"
-   git push heroku master (optional)
-   git push origin master
-   heroku logs --tail

_**Note:** Link to MySQL Server - https://remotemysql.com/databases.php_

## Color Palette

-   #2E2966 Jacarta
-   #433B84 Victoria
-   #4C4B8B East Bay
-   https://coolors.co/231d59-312d68-39367d-413e85-4b4988

## Extras

Run SQL queries using running this command inside MySQL Command Line

`source \home\user\Desktop\test.sql;`
