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
The current username is `admin` and the password is `admin` (stored as Base64 decoded for security). This mechanism can be updated in the future to improve security.

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
spring.profiles.active=dev

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
git remote add origin https://github.com/rohansharma06/Video-Engagement-Statistics.git
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
The application will run at `http://localhost:8080`.  
API documentation can be accessed at `http://localhost:8080/swagger-ui/index.html#`.

## Contact Information
**Project Maintainer**: Ruchir Bisht
**Repository**: [Video Engagement Statistics System](https://github.com/bisht-ruchir-007/video-stream-app)

---

This README is designed to give a comprehensive overview of the project, including all key sections like security, features, setup, and configuration. Let me know if you need any adjustments!