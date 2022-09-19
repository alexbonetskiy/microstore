Microstore is a REST API for web store based on microservice architecture with Spring Boot, Spring Cloud and Docker.

![microstore](https://user-images.githubusercontent.com/85827016/191137693-d08cc63d-df0a-4482-b527-ac708e760965.jpg)

Consist of the following microservices: 
 - api gateway,
 - service registry,
 - configuration server,
 - user service (authorization server),
 - order service,
 - item service.

Inter service communication is synchronous through Feign client.
Each microservice has its own H2 database, in-memory implementation for test simplicity.

####User service

The user service acts as authorization service and provides simple user management service.
Service is used for user authentication (SSO) and authorization via OAuth2 token.

API routes

Method	| Path	| Description	| User authenticated	
------------- | ------------------------- | ------------- |:-------------:|
GET	| /profile	| Get info about logged in user	|
POST| /profile	| Create new user 	| × |
PUT	| /profile	| Update user info	|
PUT | /users | Create new user | ADMIN
GET | /users/{id}| Get specified user info| ADMIN
DELETE| /users/{id}| Delete specified user| ADMIN
PUT| /users/{id}| Update specified user info| ADMIN


####Order service

The order service allows to manage the Shopping Basket/Cart. It also allows the user to retrieve their order history.

API routes

Method	| Path	| Description	| User authenticated
------------- | ------------------------- | ------------- |:-------------:|
GET	| /orders	| Get order history |  
GET| /orders/current	| Get actual order info 	| 
POST| /orders/current?id={itemId}&quantity={qty}| Add item to basket |  
DELETE| /orders/current?id={itemId} | Delete item from basket|
POST| /orders/current/confirm | Confirm order |

####Item service

This service represents item catalog.

API routes 

Method	| Path	| Description	| User authenticated
------------- | ------------------------- | ------------- |:-------------:|
GET	| /items	| Get all items |
GET| /items/{id}	| Get specified item	|
POST| /items| Add item to catalog | ADMIN
DELETE| /items/{id} | Delete specified item| ADMIN
PUT| /items/{id} | Update specified item | ADMIN


####Build and start

 - Install Docker and Docker Compose

 - Build the project: mvn package [-DSkipTests]
 
Type in terminal "docker-compose up" to pull all latest images from DockerHub.

If you'd like to start applications in Intellij Idea you need to export environment variable from ".env" file to your system before you start it.

All request goes through api gateway service - http://localhost:8000.
To look all registered services - http://localhost:8761 (Eureka Dashboard).


