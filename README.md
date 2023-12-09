# Post Data Consumption Process with Spring Boot and Kafka

This project implements a post data consumption process using Spring Boot and Kafka. The process involves four components:

![image](https://github.com/arun918kumar/spring_kafka_example1/assets/57558671/afe958dd-66a7-4f50-820f-afbc19aebb3a)

## Components

1. **Post Data Producer:**
   - Fetches post data from an external API and sends it to a Kafka topic.

2. **Post Topic:**
   - Receives post data from the Post Data Producer and forwards it to the Post Data Consumers.

3. **Post Data Consumer 1:**
   - Consumes post data from the Post Topic and persists it to an in-memory H2 database.

4. **Post Data Consumer 2:**
   - Consumes post data from the Post Topic and persists it to a NoSQL document database (Firebase Firestore).

## Getting Started

To set up and run the project, you need to:

- Install Kafka.
- Create a Kafka topic called `post-data-topic`.
- Build and run the Post Data Producer, Post Data Consumer 1, and Post Data Consumer 2 components.

## Usage

- To send post data to the system, use the Post Data Producer component. The component accepts post data in JSON format.
- To consume post data from the system, use either Post Data Consumer 1 or Post Data Consumer 2. Post Data Consumer 1 persists post data to a relational database, while Post Data Consumer 2 persists post data to a document database.

## Setup Instructions

1. Install Kafka.
2. Create a Kafka topic named `post-data-topic`.
3. Build and run the following components:
    - Post Data Producer
    - Post Data Consumer 1
    - Post Data Consumer 2

## Example Usage

```bash
# Build and run Post Data Producer
./gradlew :post-data-producer:bootRun

# Build and run Post Data Consumer 1
./gradlew :post-data-consumer-1:bootRun

# Build and run Post Data Consumer 2
./gradlew :post-data-consumer-2:bootRun
