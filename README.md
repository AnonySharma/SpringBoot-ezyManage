Run SQL queries using running this command inside MySQL Command Line

`source \home\user\Desktop\test.sql;`

# TODO:

-   Fix the links in the README.md file.
-   Write dummy data for the database in queries.sql.
-   Implement transactions
-   **Separate ADMIN, CUSTOMER, OWNER and STAFF**
-   **Implement the shops, in which a user works as a staff**
-   Fix Authorization

# Bugs:

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

# Initial Commands:

-   git init
-   git add .
-   git commit -m "test"
-   heroku create
-   heroku git:remote -a ezy-manage
-   git push heroku master
-   git push origin master
-   heroku logs --tail

# Testing Commands:

-   git add .
-   git commit -m "test"
-   git push heroku master
-   git push origin master
-   heroku logs --tail

_**Note:** Link to MySQL Server - https://remotemysql.com/databases.php_

---

# Color Palette

-   #2E2966 Jacarta
-   #433B84 Victoria
-   #4C4B8B East Bay
-   https://coolors.co/231d59-312d68-39367d-413e85-4b4988
