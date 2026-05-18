# Article Management API

Backend REST API service for managing articles, built with Java 21, Spring Boot, Spring Data JPA, Spring Security and PostgreSQL.

The project demonstrates a typical backend service structure: REST controllers, service layer, persistence layer, security configuration, Docker-based local environment and PostgreSQL integration.

## Tech stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- PostgreSQL
- Maven
- Docker
- Docker Compose

## Features

- Create articles
- Get article list
- Get article statistics
- PostgreSQL persistence
- Secured endpoints with Spring Security
- Docker Compose setup for local development
- Layered architecture: controller, service, repository, entity

## Project structure

```text
src/main/java
├── controller
├── service
├── repository
├── entity
├── config
└── dto
