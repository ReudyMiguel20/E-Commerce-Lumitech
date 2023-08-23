# E-Commerce Project (Lumitech)

## Project Overview

This project is an E-Commerce website designed for selling a wide range of tech and electronic components and devices. The backend is built using SpringBoot, while the frontend is developed using HTML, CSS, and JavaScript.


## Table of Contents

- [Project Overview](#project-overview)
- [Key Features](#key-features)
- [Core Project Elements](#core-project-elements)
- [Explaining Roles](#explaining-roles)

## Key Features

Explore the core elements and functionalities that power this project:

### Core Project Elements

- **Spring Security:** Ensures secure access to the application.
- **JPA (Java Persistence API):** Manages relational data in Java applications.
- **Hibernate:** ORM (Object-Relational Mapping) framework for Java.
- **Validation:** Ensures data integrity and consistency.
- **JWT Token:** JSON Web Tokens for authentication.
- **Basic Authentication:** Standard username and password authentication.
- **Exception Handling for Various Cases:** Gracefully handles errors.
- **Sort and Pagination:** Efficiently organizes and displays data.
- **HTML:** Markup language for creating web pages.
- **CSS:** Stylesheets for web page presentation.
- **JavaScript:** Adds interactivity and functionality to web pages.
- **H2 & PostgreSQL:** Relational database management systems used in development.
- **JUnit:** Testing framework for Java applications.
- **Mockito:** Mocking framework for unit testing.
- **Lombok:** Reduces boilerplate code and simplifies Java development.

### Explaining Roles

**Administrators:** 

Administrators have specific restrictions:

- Cannot add products to the cart (They cannot make purchases).
- Access to an admin dashboard for managing Users, Products and Orders, allowing CRUD operations.

**Customers:**

Customers are able to add products to their cart which then they can confirm to go to checkout and then able to place an order. 

Customers can add and remove products from their carts, they cannot do that after the order has been placed.

Customers cannot access certain endpoints which are only available for Administrators.
