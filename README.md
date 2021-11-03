Run SQL queries using running this command inside MySQL Command Line

`source \home\user\Desktop\test.sql;`

# TODO:

-   Fix static links make it th:href instead of href.
-   Fix the links in the README.md file.
-   Write dummy data for the database in queries.sql.

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
-   ROLE_USER, ROLE_OWNER
-   ROLE_USER, ROLE_STAFF
