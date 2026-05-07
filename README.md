<div align="center">

# 🔔 Micro-Chat: Notification Service

**The core, event-driven microservice responsible for real-time user alerts via WebSocket/STOMP integration.**

<br/>

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)](#)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen?style=for-the-badge&logo=spring-boot)](#)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-Event_Driven-FF6600?style=for-the-badge&logo=rabbitmq)](#)
[![Redis](https://img.shields.io/badge/Redis-Caching-DC382D?style=for-the-badge&logo=redis)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge&logo=postgresql)](#)

</div>

<br/>

## 🏗️ Architecture Design

This service is strictly built using **Clean Architecture** and **Domain-Driven Design (DDD)** principles to ensure high testability and complete decoupling of business logic from infrastructure frameworks.

- 📦 **Domain Layer:** Contains core business entities (`Notification`) and pure business rules.
- ⚙️ **Application Layer (Use Cases):** Orchestrates the flow of data using Gateways (`NotificationGateway`, `MessageBrokerGateway`) without knowing about the underlying database or message broker implementations.
- 🔌 **Infrastructure Layer:** Implements the Gateways (Spring Data JPA, RabbitTemplate, RedisTemplate) and handles external concerns like JWT security and Token Blacklisting.
- 🌐 **Presentation (Controller) Layer:** Exposes RESTful endpoints documented via OpenAPI/Swagger.

<br/>

## 🛠️ Environment Variables

To run this service locally or in a Kubernetes cluster, you must configure the following environment variables. If left blank, the application will default to the fallback values listed below.

### 🗄️ Database & Caching

| Variable | Description | Default Fallback |
| :--- | :--- | :--- |
| `SPRING_DATASOURCE_URL` | PostgreSQL connection string | `jdbc:postgresql://localhost:5432/db_micro_chat` |
| `SPRING_DATASOURCE_USERNAME` | PostgreSQL username | `user` |
| `SPRING_DATASOURCE_PASSWORD` | PostgreSQL password | `password` |
| `SPRING_DATA_REDIS_HOST` | Redis instance host | `localhost` |

### 🐇 Message Broker (RabbitMQ)

| Variable | Description | Default Fallback |
| :--- | :--- | :--- |
| `SPRING_RABBITMQ_HOST` | RabbitMQ broker host | `localhost` |
| `SPRING_RABBITMQ_CLIENT_USERNAME` | STOMP Client login | `guest` |
| `SPRING_RABBITMQ_CLIENT_PASSWORD` | STOMP Client password | `guest` |
| `SPRING_RABBITMQ_SYSTEM_USERNAME` | Broker admin/system login | `guest` |
| `SPRING_RABBITMQ_SYSTEM_PASSWORD` | Broker admin/system password | `guest` |

### 🔒 Security 

| Variable | Description | Default Fallback |
| :--- | :--- | :--- |
| `JWT_SECRET` | Base64 encoded secret for JWT validation | `404E63...` |


<br/>

## 🚀 Running the Application

### 1️⃣ Local Development (Docker Compose)
If you are running the infrastructure locally via Docker Compose:

```bash
# Build the application skipping tests for speed
./mvnw clean package -DskipTests

# Run the Spring Boot app
java -jar target/micro-chat-notification-service-0.0.1-SNAPSHOT.jar

```
### 2️⃣ Kubernetes Deployment (Minikube / Production)

This service is designed to be orchestrated via Kubernetes. Build the image and apply the manifests found in the central ecosystem repository:
Bash

```bash
# Build the Docker image locally
docker build -t notification-service:latest .

# Apply the Kubernetes manifest
kubectl apply -f k8s/08-notification-service.yaml

```

### 📚 API Documentation (Swagger)

This service utilizes springdoc-openapi for API documentation.

When running within the full ecosystem, the Swagger UI for this microservice is centrally aggregated and accessible via the API Gateway:

    🔗 Gateway Swagger UI: http://api.microchat.local/swagger-ui.html

    (Select "Notification Service" from the top-right dropdown menu).

If running in isolation, the raw OpenAPI JSON spec can be accessed at:

http://localhost:8083/v3/api-docs