## 📌 Technologies
- Java 21
- Spring Boot
- Spring Batch
- PostgreSQL
- RabbitMQ
- Docker

<br>

## 🎯 What is it ?
It is a web project developed with spring batch, with the objective of migrating data from one side to another.

<br>

## Architecture and data flux 
The spring project have an UISwing, responsable by send message to RabbitMQ.
By other side too had two jobs, that listen an queue from RabbitMQ.
When anyone job receive an message, this job is started, where do all procecessing of data.

<br>


## 🚀 How to execute the project ?

### In ``root`` folder execute

- sudo docker-compose down -v <br><br>
- sudo docker-compose up <br><br>
- mvn clean package <br><br>
- mvn spring-boot:run <br><br>
