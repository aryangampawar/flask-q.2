# Publish/Subscribe Assignment using Web Services, ESB, and JMS

## Overview
This project demonstrates the implementation of the Publish/Subscribe paradigm using Java, integrating:
- **Web Services**: A RESTful web service using JAX-RS (Jersey) to publish messages.
- **Enterprise Service Bus (ESB)**: Apache Camel for routing messages.
- **Java Message Service (JMS)**: Apache ActiveMQ as the JMS provider for topic-based messaging.

## Prerequisites
- Java 8 or higher
- Maven
- Apache ActiveMQ (running on `tcp://localhost:61616`)
- Internet connection for Maven dependencies

## Project Structure
- `pom.xml`: Maven configuration with dependencies.
- `src/main/java/com/assignment/`:
  - `MessagePublisher.java`: Publishes messages to a JMS topic.
  - `MessageSubscriber.java`: Subscribes to the JMS topic and receives messages.
  - `MessageWebService.java`: RESTful web service to accept messages via HTTP POST.
  - `ESBRoute.java`: Apache Camel route for message routing.
- `src/main/resources/camel-context.xml`: Camel configuration for ESB routing.
- `README.md`: This file.

## Setup Instructions
1. **Install and Start ActiveMQ**:
   - Download and install Apache ActiveMQ from `http://activemq.apache.org/`.
   - Start ActiveMQ: `./activemq start` (default broker URL: `tcp://localhost:61616`).

2. **Build the Project**:
   ```bash
   mvn clean install
3. Run the Components:

Web Service: Run MessageWebService to start the RESTful service on http://localhost:8080/publish.

Subscribers: Run MessageSubscriber with a unique argument, e.g.:

java -cp target/PubSubAssignment-1.0-SNAPSHOT.jar com.assignment.MessageSubscriber Sub1
java -cp target/PubSubAssignment-1.0-SNAPSHOT.jar com.assignment.MessageSubscriber Sub2

ESB Route: Run ESBRoute to start the Camel route.

Publisher: Run MessagePublisher to send a test message, or use the web service.

Test the Web Service:

Use curl or a tool like Postman to send a POST request:

curl -X POST http://localhost:8080/publish -d "Test message from web service"

How It Works

Publish: The MessageWebService accepts HTTP POST requests with a message, which is forwarded to the MessagePublisher.

JMS Topic: The publisher sends messages to a JMS topic (NewsTopic) using ActiveMQ.

Subscribe: Multiple MessageSubscriber instances listen to the topic and print received messages.

ESB: Apache Camel routes messages from the JMS topic to a log endpoint, demonstrating ESB integration.

Notes

Ensure ActiveMQ is running before starting the application.

The project uses Maven for dependency management, so all required libraries are automatically downloaded.

The ESB route logs messages to the console, but you can extend it to other endpoints (e.g., email, database).

For production, consider adding error handling and persistent messaging.
Submission Checklist
Source code (src/)
pom.xml
README.md
Screenshots of running the application (web service, subscribers, and ESB logs)
Brief report explaining the implementation (optional, if required by the teacher)