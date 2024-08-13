
# Java Spring Boot Application with PostgreSQL

This is a Java Spring Boot application that demonstrates basic CRUD operations with a PostgreSQL database. The application uses Spring Data JPA for data persistence and Spring Security for securing the endpoints.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [API Endpoints](#api-endpoints)
- [Troubleshooting](#troubleshooting)

## Prerequisites

- Java 21
- Docker
- Docker Compose

## Setup

1. Clone this repository:

    ```bash
    git clone https://github.com/wadimbap/Rest-test.git
    cd Rest-test
    ```

2. Build the project using Maven:

    ```bash
    ./mvnw clean install
    ```

## Running the Application

To run the application locally with Docker:

1. Build the container:

    ```bash
    docker-compose build
    ```

2. Run the container with Spring Boot application:

    ```bash
    docker-compose up
    ```

   The application will start and listen on `http://localhost:8080`.

## Testing

You can test the application using tools like [Postman](https://www.postman.com/) or `curl`.

For example, to test the `POST /api/articles/create` endpoint:

```bash
curl -X POST http://localhost:8080/api/articles/create \
    -H "Content-Type: application/json" \
    -d '{"title": "Test Title", "author": "Author", "content": "Sample Content", "publishedDate": "2024-08-13"}'
```


## API Endpoints

- `POST /api/articles/create`: Create a new article.
- `GET /api/articles`: List all articles.
- `GET /api/articles/stats`: Get article statistics (requires ADMIN role).

## Troubleshooting

- **403 Forbidden Error**: If you encounter a 403 error while trying to create an article, it may be due to CSRF protection. Ensure that CSRF is properly handled or disabled in your tests and Postman requests.

- **Database Connection Issues**: Make sure PostgreSQL is running and accessible. Check the connection settings in `application.properties`.

- **Docker Issues**: If you encounter issues with Docker, ensure Docker and Docker Compose are installed and running properly.
