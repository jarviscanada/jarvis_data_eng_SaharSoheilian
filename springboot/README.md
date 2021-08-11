Table of contents 

*[Introduction](#Introduction)

*[Test](#Test)

# Introduction
Stock trading application is a java project using REST API to allow users to:

 - Manage client profiles and accounts (ex. create a new trader, deposit or withdraw fund to/from a trader account)
 - Monitor trade securities (ex. get quotes)
 
Technologies deployed in this project:

- Core Java
- Springboot: to implement REST API which handles core business logic ex. managing trader profile, 
executing security orders
- IEX Cloud REST APIs
- Docker
- Git
- JUnit4
- Intellij


Designed based on the MVC design pattern and used Springboot to fetch data from the IEX Cloud
via its REST API. Persisted the application data in PostgreSQL database since Springboot is stateless.
Tested the trading app by developing integration test cases using JUnit4 and tested REST endpoints
using Swagger and Postman. For easier deployment, dockerized the application and PostgreSQL database
using a docker network for connection purposes. Used GitHub to manage the source code.


- Controller layer: handles user requests, receives HTTP requests and calls the corresponding 
  Service methods and then return the results to user via HTTP, like getting the market data from 
  the IEX (get quotes, update a quote, add a new ticker), and managing trader and account information
  (deposit and withdraw fund from a given account)

- Service layer: does the business logic on creating, deleting a trader, deposit/withdraw fund, 
  finding quotes and updating db 

- DAO layer: handles models and calls httpHelper/JdbcTemplate to access underlying service(IEX Cloud)/
  storage(psql)

- SpringBoot: webservlet/TomCat and IoC
- PSQL and IEX

View: Trader Account wrapper class

#Test
- JUnit4
- Code coverage: is a measurement of how many lines/blocks of the code are executed while the automated
tests are running. For time-saving purposes, considered the line coverage of greater than 60% for all service 
and DAO classes.

