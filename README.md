# demo-springboot-rest-hibernate-mysql

- Summary:
    - This project is a back-end app for "note-management tool".
- Features:
    - This is a simple project to illustrate creating REST APIs using springboot, hibernate, and mysql.
    - Exposes relevant APIs for `User`, `Note`, and  `Genre` resources. Refer to [APIs](#apis) section for details.
    - Covers CRUD (create, read, update, delete) operations for `User` and `Note` resources. Refer to [APIs](#apis) section for details.
    - Covers all 3 entity-relationships (`one-to-one`, `one-to-many`, `many-to-many`). Refer
      to [Entity-Relationship Model](#entity-relationship-model) section for details.
    - All requests are validated before processing. If request validation fails, an error response will be returned with corresponding error code.

- Limitations:
    - Access-validation is not implemented; anyone can access any API.
    - Passwords are saved as plain-text (not encrypted/hashed).

# Tech-stack:

- java 17
- spring boot 3.4.0
- JPA
- hibernate
- mysql
- lombok

# APIs:

```
- GET    : /users
- GET    : /users/{id}
- GET    : /users/{id}/notes
- POST   : /users
- PUT    : /users/{id}
- DELETE : /users/{id}

- GET    : /notes
- GET    : /notes/{id}
- POST   : /notes 
- PUT    : /notes/{id}
- DELETE : /notes/{id} 

- GET    : /genres
```

# Database Model

<img title="a title" alt="Alt text" src="docs/er.png">

# Entity-Relationship Model

```
login_credential <---one-to-one ---> user <--- one-to-many ---> note <--- many-to-many ---> genre                                                 
```

# Getting Started:

1. Clone the project.
2. Make sure mysql server is up and running on port 3306.
3. Update mysql username, password in `application-yml`, if needed.
4. Run the project.

# Known Bugs:


