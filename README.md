# Video Engagement Statistics System

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Requirements](#requirements)
- [Configuration](#configuration)
    - [Database Setup](#setup-database)
    - [Application Properties](#application-properties)
- [Assumptions](#assumptions)
- [Server Deployment](#server-deployment)
- [Installation](#installation)
    - [Clone the Repository](#clone-the-repository)
    - [Build the Project](#build-the-project)
- [Usage](#usage)
    - [Run the Application](#run-the-application)
    - [Access the APIs](#access-the-apis)
- [Authentication API Documentation](#authentication-api-documentation)
- [Video Engagement API Documentation](#video-engagement-api-documentation)
- [Video Management API Documentation](#video-management-api-documentation)
- [Contact Information](#contact-information)

---

## Description
This repository contains the **Video Engagement Statistics System** built using **Spring Boot**. The system tracks and manages video engagement data such as impressions, views, and interactions for a video streaming platform. It includes several services for handling video content, retrieving engagement statistics, and managing video metadata.

---

### Security Architecture

#### JWT Authentication
The system uses **JWT (JSON Web Tokens)** for securing endpoints. Sensitive operations, such as fetching video content and engagement statistics, are only accessible by authenticated users. JWT tokens are stateless and do not require storing session data on the server, enabling scalability.

#### Token Generation and Refresh
- **Token Generation**: `POST /v1/admin/get-token` (with basic authentication credentials: `username: admin`, `password: admin`).
- **Token Refresh**: `POST /v1/admin/token-refresh` (providing a refresh token: `refreshToken: refreshToken`).

#### Credentials
Users must first **register** themselves before consuming APIs.

#### Secure Endpoints
All endpoints are secured and require a valid JWT token for access.

#### JWT Token Expiration
JWT tokens expire in **1 hour**, and the refresh token remains valid for **2 hours**.

---

## Features
- **Video Content Retrieval**: Fetch video metadata and engagement statistics for a given video ID.
- **Search Video Metadata**: Search for video metadata (title, director, genre) with pagination.
- **Track Engagement**: Track video engagement (impressions, views).
- **Soft Delete Video**: Soft delete videos while keeping them in the database.
- **Publish Video**: Add videos to the platform.
- **Video Metadata Management**: Add, edit, and update video metadata.
- **Error Handling**: Robust error management for issues like invalid video IDs.

---

## Requirements
- **Java**: 21
- **MySQL**: 8.0.33
- **Maven**: 3.2.12 or higher
- **JUnit 5 & Mockito**: For unit testing
- **Kafka**: (Production environment)

---

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
Add the following configuration to `src/main/resources/application.properties`:

```properties
# Active Profile
spring.profiles.active=dev // For Production: prod

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

---

## Assumptions
1. **Development Environment**: Direct database communication is used. In production, **Kafka Message Brokers** will handle inter-service communication.
2. **Caching**: No caching is implemented in development. In production, **Redis Distributed Caching** will be applied.
3. **Logging**: Logs are shown on the console in development. In production, they will be sent to **Elasticsearch** via **Kafka-Logstash** integration.

---

## Server Deployment
- **Java 21**, **MySQL**, and **Tomcat** are required.
- The deployment package can be a **WAR** or **JAR** file.
- Upload the **SQL dump** to the server database.

---

## Installation

### Clone the Repository
Clone the repository:

```bash
git clone https://github.com/bisht-ruchir-007/video-stream-app
cd video-stream-app
```

### Build the Project
Build the project with Maven:

```bash
mvn clean install
```

---

## Usage

### Run the Application
To start the application:

```bash
mvn spring-boot:run
```

### Access the APIs
Once the application is running, you can interact with the various API endpoints detailed below.

---

## Authentication API Documentation

### 1. User Registration
- **URL**: `/api/v1/auth/register`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
      "username": "user123",
      "password": "password123"
    }
    ```
- **Response**:
    ```json
    {
      "message": "User registered successfully",
      "data": { "token": "JWT_TOKEN_HERE" }
    }
    ```

### 2. User Login
- **URL**: `/api/v1/auth/login`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
      "username": "user123",
      "password": "password123"
    }
    ```
- **Response**:
    ```json
    {
      "message": "Login successful",
      "data": { "token": "JWT_TOKEN_HERE" }
    }
    ```

### 3. Refresh Token
- **URL**: `/api/v1/auth/refresh-token`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
      "refreshToken": "REFRESH_TOKEN_HERE"
    }
    ```
- **Response**:
    ```json
    {
      "message": "Token refreshed successfully",
      "data": { "accessToken": "NEW_ACCESS_TOKEN_HERE", "refreshToken": "NEW_REFRESH_TOKEN_HERE" }
    }
    ```

---

## Video Engagement API Documentation

### 1. Play Video
- **URL**: `/api/v1/videos/{id}/play`
- **Method**: `GET`
- **Path Variable**: `id` (Video ID)
- **Response**:
    ```json
    {
      "message": "Video playing successfully",
      "data": "Sample Video Content"
    }
    ```

### 2. Search Videos by Director
- **URL**: `/api/v1/videos/director`
- **Method**: `GET`
- **Request Parameters**: `director`, `page`, `size`
- **Response**:
    ```json
    {
      "message": "Videos found",
      "data": [ /* video details */ ]
    }
    ```

### 3. Get Engagement Stats for Video
- **URL**: `/api/v1/stats/{id}/engagement`
- **Method**: `GET`
- **Path Variable**: `id` (Video ID)
- **Response**:
    ```json
    {
      "message": "Engagement stats fetched successfully",
      "data": { "views": 1000, "impressions": 500 }
    }
    ```

---

## Video Management API Documentation

### 1. Publish Video
- **URL**: `/api/v1/videos/publish`
- **Method**: `POST`
- **Request Body**:
    ```json
    {
      "title": "Video Title",
      "description": "Video description",
      "director": "Director Name",
      "releaseDate": "2025-03-13"
    }
    ```
- **Response**:
    ```json
    {
      "message": "Video published successfully",
      "data": { /* video details */ }
    }
    ```

### 2. Edit Video Metadata
- **URL**: `/api/v1/videos/edit/{id}`
- **Method**: `PUT`
- **Request Body**:
    ```json
    {
      "title": "Updated Title",
      "description": "Updated Description",
      "director": "Updated Director",
      "releaseDate": "2025-03-15"
    }
    ```

### 3. Delist Video
- **URL**: `/api/v1/videos/delist/{id}`
- **Method**: `DELETE`
- **Response**:
    ```json
    {
      "message": "Video has been delisted successfully",
      "data": "Video with ID: 1 has been removed from the catalog"
    }
    ```

---

## Contact Information

**Project Maintainer**: Ruchir Bisht  
**Repository**: [Video Engagement Statistics System](https://github.com/bisht-ruchir-007/video-stream-app)

---