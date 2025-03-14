#  Video Streaming and Engagement Statistics Module 

## **Table of Contents**
1. [Description](#description)
2. [Features](#features)
3. [Requirements](#requirements)
4. [Configuration](#configuration)
  - [Database Setup](#database-setup)
  - [Application Properties](#application-properties)
5. [Assumptions](#assumptions)
6. [Deployment](#deployment)
7. [Installation](#installation)
  - [Clone the Repository](#clone-the-repository)
  - [Build the Project](#build-the-project)
8. [Usage](#usage)
  - [Run the Application](#run-the-application)
  - [Access the APIs](#access-the-apis)
9. [API Documentation](#api-documentation)
  - [Authentication API](#authentication-api)
  - [Video Engagement API](#video-engagement-api)
  - [Video Management API](#video-management-api)
10. [Swagger Documentation](#swagger-documentation)
11. [Contact Information](#contact-information)

---

## **Description**
The **Video Engagement & Management System** is a **Spring Boot** application designed for **video streaming platforms** to handle:
- **Video content management**
- **Metadata handling**
- **User engagement analytics (views, impressions, interactions)**
- **Secure authentication & authorization using JWT**
- **Optimized API endpoints for high performance**

This system follows a **scalable microservices architecture** with **Spring Security, efficient data handling, and fault-tolerant mechanisms** to ensure reliability and scalability.

---

## **Security Architecture**

### **JWT Authentication**
- **JWT (JSON Web Tokens)** secures all endpoints.
- Users must **register** or **log in** before consuming APIs.
- JWT tokens have an **expiration of 1 hour**, and refresh tokens last for **2 hours**.
- Secure endpoints require a **valid JWT token** for access.

---

## **Features**

### **1. Video Management (`VideoManagementController`)**
✅ **Publish Video** - `POST /publish`  
✅ **Edit Video Metadata** - `PUT /edit/{id}`  
✅ **Delist Video** - `DELETE /delist/{id}`  
✅ **List Videos** - `GET /list`

### **2. Video Engagement (`VideoEngagementController`)**
✅ **Load Video Content** - `GET /load/{id}`  
✅ **Play Video** - `GET /play/{id}`  
✅ **Search Videos by Director** - `GET /search/director`  
✅ **Search Videos** - `GET /search`  
✅ **Get Engagement Stats** - `GET /stats/engagement/{id}`

### **3. Authentication (`AuthController`)**
✅ **Register User** - `POST /register`  
✅ **Login User** - `POST /login`  
✅ **Refresh Token** - `POST /refresh-token`

### **4. Additional Features**
✅ **Spring Security with JWT Authentication**  
✅ **Custom Exception Handling (`VideoNotFoundException`, `VideoAlreadyPresentException`)**  
✅ **Logging with SLF4J** for API request tracking  
✅ **Pagination Support** for video listings & searches  
✅ **Clean Architecture** using **Service and ServiceImpl layers**

---

## **Requirements**
- **Java**: 21
- **Spring Boot**: Latest Version
- **MySQL**: 8.0.33
- **Maven**: 3.2.12+
- **JUnit 5 & Mockito**: For unit testing
- **Kafka**: Used in production environment

---

## **Configuration**

### **Database Setup**
1. **Create the MySQL database:**
   ```sql
   CREATE DATABASE video_db;
   ```
2. **Load the database dump:**
   ```bash
   mysql -u{username} -p{password} video_db < src/main/resources/dbDump.sql
   ```

### **Application Properties**
Add the following configuration to `src/main/resources/application.properties`:

```properties
# Active Profile
spring.profiles.active=dev  # Use 'prod' for production

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/video_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# Logging Configuration
logging.file.path=your_log_path
logging.file.name=your_file_name

# JWT Configuration
jwt.secret=desiredSecretKey
jwt.expiration=3600 # 1 hour in seconds
jwt.refreshExpiration=7200 # 2 hours in seconds
```

---

## **Assumptions**
1. **Development**: Direct database communication. In production, **Kafka Message Brokers** will be used.
2. **Caching**: Redis Distributed Caching will be applied in production.
3. **Logging**: Console logging in development. Logs will be sent to **Elasticsearch** in production via **Kafka-Logstash**.
4. **Rate Limiting**: Not implemented in development but will be added in production.
5. **Video Engagement Tracking**: Load and play operations are handled independently.

---

## **Deployment**
- Requires **Java 21, MySQL, and Tomcat**.
- The deployment package can be **WAR/JAR**.
- **SQL dump** should be uploaded to the production database.

---

## **Installation**

### **Clone the Repository**
```bash
git clone https://github.com/bisht-ruchir-007/video-stream-app
cd video-stream-app
```

### **Build the Project**
```bash
mvn clean install
```

---

## **Usage**

### **Run the Application**
```bash
mvn spring-boot:run
```

### **Access the APIs**
Once the application is running, APIs can be accessed as per the documentation.

---

## **API Documentation**

### **Authentication API**
#### **1. User Registration**
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

#### **2. User Login**
- **URL**: `/api/v1/auth/login`
- **Method**: `POST`
- **Response**:
  ```json
  {
    "message": "Login successful",
    "data": { "token": "JWT_TOKEN_HERE" }
  }
  ```

#### **3. Refresh Token**
- **URL**: `/api/v1/auth/refresh-token`
- **Method**: `POST`
- **Response**:
  ```json
  {
    "message": "Token refreshed successfully",
    "data": { "accessToken": "NEW_ACCESS_TOKEN_HERE", "refreshToken": "NEW_REFRESH_TOKEN_HERE" }
  }
  ```

### **Video Engagement API**
#### **1. Play Video**
- **URL**: `/api/v1/videos/{id}/play`
- **Method**: `GET`
- **Response**:
  ```json
  {
    "message": "Video playing successfully",
    "data": "Sample Video Content"
  }
  ```

#### **2. Search Videos by Director**
- **URL**: `/api/v1/videos/director`
- **Method**: `GET`
- **Response**:
  ```json
  {
    "message": "Videos found",
    "data": [ /* video details */ ]
  }
  ```

#### **3. Get Engagement Stats**
- **URL**: `/api/v1/stats/{id}/engagement`
- **Method**: `GET`
- **Response**:
  ```json
  {
    "message": "Engagement stats fetched successfully",
    "data": { "views": 1000, "impressions": 500 }
  }
  ```

---

## **Swagger Documentation**
To explore APIs, visit:  

Swagger UI - http://localhost:8080/swagger-ui/index.html

---

## **Contact Information**
📌 **Project Maintainer**: Ruchir Bisht  
📌 **GitHub Repository**: [Video Engagement Statistics System](https://github.com/bisht-ruchir-007/video-stream-app)

---

This README follows industry best practices with **clear structuring, professional formatting, and comprehensive details** to ensure ease of use and scalability. 🚀