# Video Engagement Statistics System

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Requirements](#requirements)
- [Configuration](#configuration)
- [Server Deployment](#server-deployment)
- [Installation](#installation)
- [Usage](#usage)
- [Contact Information](#contact-information)

## Description
This repository contains a **Video Engagement Statistics System** built using **Spring Boot**. The system tracks and manages video engagement data such as impressions, views, and interactions for a video streaming platform. It includes several services for handling video content, retrieving engagement statistics, and managing video metadata.

### Detailed Documentation and Assumptions

#### Security Architecture

**JWT Authentication**  
To secure endpoints, **JWT (JSON Web Tokens)** is used for authentication. Sensitive operations like fetching video content and engagement statistics are only accessible by authenticated users.  
JWT tokens are stateless and do not require storing session data on the server side, making the system scalable.

**Token Generation and Refresh**  
Two key API endpoints are available for token handling:
- `POST /v1/admin/get-token`: This endpoint generates a JWT token using basic authentication credentials (`username: admin`, `password: admin`).
- `POST /v1/admin/token-refresh`: This endpoint generates a new JWT token using a refresh token provided during the token generation process (`refreshToken: refreshToken`).

**Credentials**  
User have to first Register Its self , then he can consume the APIs .

**Secure Endpoints**  
All other endpoints in the application are secured and require a valid JWT token to access.

**JWT Token Expiration**  
JWT tokens have an expiration time of 1 hour, and the refresh token validity is double the token expiration time.

#### Design Decisions

**Architecture**
- **Spring Boot Framework**: Chosen for its ease of use, scalability, and built-in support for REST APIs.
- **JPA/Hibernate**: Used for database interactions, simplifying data persistence.
- **MySQL Database**: Chosen for its high performance and relational data storage for video metadata and engagement statistics.
- **Error Handling**: Centralized error handling through a custom exception (`InternalServerErrorException`).

**Engagement Statistics Tracking**
- **Kafka Integration (Optional)**: Supports Kafka for tracking engagement events asynchronously, improving scalability.
- **Event-driven Design**: Engagement events are asynchronously processed, enabling real-time tracking.

**Pagination and Search Functionality**  
Search operations support multiple fields (title, director, genre) and pagination for efficiency.

**Soft Delete**  
The system uses a soft delete approach for video content, allowing videos to remain in the database after deletion but preventing them from being included in search results or engagement tracking.

#### Features
- **Video Content Retrieval**: Fetch video metadata and engagement statistics for a given video ID.
- **Search Video Metadata**: Allows searching video metadata (title, director, genre, etc.) with pagination.
- **Track Engagement Statistics**: Track video engagement such as impressions and views.
- **Soft Delete Video**: Mark videos as deleted without removing them from the database.
- **Publish Video**: Add videos to the platform.
- **Video Metadata Management**: Add, edit, and update metadata such as title, director, etc.
- **Error Handling**: Robust error management for common issues like invalid video IDs or unexpected errors.

## Requirements
- **Java**: 21
- **MySQL**: 8.0.33
- **Maven**: 3.2.12 or higher
- **JUnit 5 & Mockito** for unit testing and mocking
- **Kafka** (Production)

## Configuration

### Setup Database
Create the MySQL database:

```sql
CREATE DATABASE database_name;
```

Install the database dump from `src/main/resources/dbDump.sql`:

```bash
mysql -u{username} -p{password} {database_name} < {path_to_dump}/dbDump.sql;
```

### Application Properties
Add common configurations in `src/main/resources/application.properties`:

```properties
# Active Profile
spring.profiles.active=dev // Prod

# Database Config
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=your_username
spring.datasource.password=your_password

# Log Config
logging.file.path=your_log_path
logging.file.name=your_file_name

# JWT Config
jwtSecretKey=desiredSecretKey
jwtExpirationTimeInSec=desiredTimeInSec

```

## Server Deployment
The server requires **Java 21**, **MySQL**, and **Tomcat**.  
The deployment package can be either a **WAR** or **JAR** file, depending on your needs.  
The SQL dump should be uploaded to the server database.

## Installation

### Clone the Repository
Clone the repository to your local system:

```bash
cd existing_repo
git remote add origin https://github.com/bisht-ruchir-007/video-stream-app
git branch -M main
git push -uf origin main
```

### Build the Project
Build the project using Maven:

```bash
mvn clean install
```

## Usage

### Run the Application
Start the application using:

```bash
mvn spring-boot:run
```

### Access the APIs
# Authentication API Documentation

## 1. User Registration
- **URL:** `/api/v1/auth/register`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
      "username": "user123",
      "password": "password123"
    }
    ```
- **Response:**
    - **Status:** 201 Created
    - **Body:**
        ```json
        {
          "message": "User registered successfully",
          "data": { "token": "JWT_TOKEN_HERE" }
        }
        ```

## 2. User Login
- **URL:** `/api/v1/auth/login`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
      "username": "user123",
      "password": "password123"
    }
    ```
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Login successful",
          "data": { "token": "JWT_TOKEN_HERE" }
        }
        ```

## 3. Refresh Token
- **URL:** `/api/v1/auth/refresh-token`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
      "refreshToken": "REFRESH_TOKEN_HERE"
    }
    ```
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Token refreshed successfully",
          "data": { "accessToken": "NEW_ACCESS_TOKEN_HERE", "refreshToken": "NEW_REFRESH_TOKEN_HERE" }
        }
        ```

# Video Engagement API Documentation

## 1. Play Video
- **URL:** `/api/v1/videos/{id}/play`
- **Method:** `GET`
- **Path Variable:**
    - `id`: The ID of the video to be played.
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Video playing successfully",
          "data": "Sample Video Content"
        }
        ```

## 2. Search Videos by Director
- **URL:** `/api/v1/videos/director`
- **Method:** `GET`
- **Request Parameters:**
    - `director`: The director's name.
    - `page`: (Optional, default: `0`)
    - `size`: (Optional, default: `10`)
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Videos found",
          "data": [
            {
              "id": 1,
              "title": "Inception - 1",
              "director": "Ruchir",
              "cast": "Leonardo DiCaprio",
              "genre": "Sci-Fi",
              "runningTime": 120
            },
            {
              "id": 2,
              "title": "Inception - 2",
              "director": "Ruchir",
              "cast": "Leonardo DiCaprio",
              "genre": "Sci-Fi",
              "runningTime": 120
            }
          ]
        }
        ```

## 3. Search Videos
- **URL:** `/api/v1/videos/search`
- **Method:** `GET`
- **Request Parameters:**
    - `searchPhrase`: The search query.
    - `page`: (Optional, default: `0`)
    - `size`: (Optional, default: `10`)
- **Response:**
    - **Status:** 200 OK (if videos found)
    - **Status:** 204 No Content (if no videos found)
    - **Body (When Videos Found):**
        ```json
        {
          "message": "Videos found",
          "data": [
            {
              "id": 1,
              "title": "Inception - 1",
              "director": "Ruchir",
              "cast": "Leonardo DiCaprio",
              "genre": "Sci-Fi",
              "runningTime": 120
            },
            {
              "id": 2,
              "title": "Inception - 2",
              "director": "Ruchir",
              "cast": "Leonardo DiCaprio",
              "genre": "Sci-Fi",
              "runningTime": 120
            }
          ]
        }
        ```

## 4. Get Engagement Stats for Video
- **URL:** `/api/v1/stats/{id}/engagement`
- **Method:** `GET`
- **Path Variable:**
    - `id`: The ID of the video to fetch engagement stats for.
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Engagement stats fetched successfully",
          "data": {
            "views": 1000,
            "impressions": 500
          }
        }
        ```


# Video Management API Documentation

## 1. Publish Video
- **URL:** `/api/v1/videos/publish`
- **Method:** `POST`
- **Request Body:**
    ```json
    {
      "title": "Video Title",
      "description": "Video description",
      "director": "Director Name",
      "releaseDate": "2025-03-13"
    }
    ```
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Video published successfully",
          "data": {
            "id": 1,
            "title": "Video Title",
            "description": "Video description",
            "director": "Director Name",
            "releaseDate": "2025-03-13"
          }
        }
        ```

## 2. Edit Video Metadata
- **URL:** `/api/v1/videos/edit/{id}`
- **Method:** `PUT`
- **Path Variable:**
    - `id`: The ID of the video to be updated.
- **Request Body:**
    ```json
    {
      "title": "Updated Video Title",
      "description": "Updated description",
      "director": "Updated Director",
      "releaseDate": "2025-03-15"
    }
    ```
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Video metadata updated successfully",
          "data": {
            "id": 1,
            "title": "Updated Video Title",
            "description": "Updated description",
            "director": "Updated Director",
            "releaseDate": "2025-03-15"
          }
        }
        ```

## 3. Delist Video
- **URL:** `/api/v1/videos/delist/{id}`
- **Method:** `DELETE`
- **Path Variable:**
    - `id`: The ID of the video to be delisted.
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Video has been delisted successfully",
          "data": "Video with ID: 1 has been removed from the catalog"
        }
        ```

## 4. List All Videos
- **URL:** `/api/v1/videos`
- **Method:** `GET`
- **Request Parameters:**
    - `page`: (Optional, default: `0`)
    - `size`: (Optional, default: `10`)
- **Response:**
    - **Status:** 200 OK
    - **Body:**
        ```json
        {
          "message": "Videos fetched successfully",
          "data": [
            {
              "id": 1,
              "title": "Video 1",
              "director": "Director A"
            },
            {
              "id": 2,
              "title": "Video 2",
              "director": "Director B"
            }
          ]
        }
        ```




## Contact Information
**Project Maintainer**: Ruchir Bisht
**Repository**: [Video Engagement Statistics System](https://github.com/bisht-ruchir-007/video-stream-app)

---

This README is designed to give a comprehensive overview of the project, including all key sections like security, features, setup, and configuration. Let me know if you need any adjustments!