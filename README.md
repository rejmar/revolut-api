# revolut-api
It is a Java RESTful API for transfering money between user accounts.

**Technologies**
- Java EE - core of application
- Jetty - application server
- Jersey - framework for REST APIs
- Jackson - managing JSON 
- JUnit - framework for Unit Testing
- REST-ASSURED - framework for REST endpoints testing
- Maven - dependencies manager, project builder
- GSON - JSON converter
- Lombok - annotation processor
- Apache HTTP Client - library for accessing HTTP resources

**How to start application**

To start application user needs to build maven project
```
mvn clean build
```

Then .jar file from *target* folder can be launch like normal Java application. It requires at least JDK 8.
```
java -jar revolut-api-1.0-SNAPSHOT.jar
```

It will start the application on localhost:8080/api.

***Services***

The application has 2 services with many endpoints. 

| HTTP METHOD | PATH | DESCRIPTION |
| ----------- | ---- | ----------- |
| GET         | /userAccount/all | get all user accounts |
| GET         | /userAccount/{id} | get user account by id |
| GET         | /userAccount/{id}/balance | get balance of user account by id |
| GET         | /userAccount/{id}/transactions | get all transactions of user account by id |
| POST         | /userAccount/create | create new user account |
| PUT         | /userAccount/{id}/deposit/{amount} | deposit amount on user account |
| PUT         | /userAccount/{id}/withdraw/{amount} | withdraw amount from user account |
| DELETE         | /userAccount/{id} | delete user account |
| GET         | /transaction/all | get all transactions |
| PUT         | /transaction/transfer | transfer money between two user accounts |

For new user account creation, user has to provide JSON object in body 
```
{
  "balance": "10000.00"
}
```

For new transaction, body looks like below
```
{
  "senderId": 1,
  "recipientId": 2,
  "amount": "1000.00"
}
```
