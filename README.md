# 🔐 Spring Boot JWT Security with Email Authentication & Role-Based Authorization

This project is a **Spring Boot** application that implements **JWT (JSON Web Token)** based security for both **authentication** and **authorization**.

## 🚀 Features

- ✅ **Email and Password-Based Authentication**
- 🔐 **JWT Token Generation** for secure access to protected endpoints
- 🛡️ **Role and Privilege-Based Authorization**
- 🔄 **Password Encoding** using `BCryptPasswordEncoder`
- 📧 **Email OTP Service** for user **signup verification**
- 📄 **Swagger OpenAPI Integration** for interactive API documentation

---

## 🧰 Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (io.jsonwebtoken)
- MySQL (can be configured)
- Swagger (Springdoc OpenAPI)
- JavaMailSender

---

## 📦 Modules Overview

| Module        | Description |
|---------------|-------------|
| **Authentication** | Email & password-based login and registration |
| **Authorization**  | Access control using roles and privileges |
| **JWT Token**      | Token issued on successful login and validated for secure endpoints |
| **Password Encoder** | Passwords are hashed using `BCryptPasswordEncoder` |
| **Email OTP**      | OTP sent to user email for signup verification |
| **Swagger UI**     | Interactive API documentation and testing |

---

- Postman collection is also available only for the Signup APIs : **How a end user can put there data. Here we only have 2 roles i.e (1, ADMIN) and (2, MEMBER)  and are some set of APIs exposed once you get the JWT token try to hit the Member and Admin related APIs.**   

## Access Swaggger UI : https://spring-jwt-security-production.up.railway.app/swagger-ui/index.html#/

## Hosted on Railway : (https://railway.com/) 


  
