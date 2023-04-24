## ASSESSMENT

##### TECH STACK
Programming Language: Java17 <br>
Framework: Spring Boot 3 <br>
Database: H2 <br>
UnitTest: JUnit5 <br>

##### SWAGGER UI
http://localhost:9090/account-api/swagger-ui/index.html#/ <br>
http://localhost:9080/transaction-api/swagger-ui/index.html#/

##### H2 DB
User: sa <br>
Password: <br>
JDBC URL: jdbc:h2:mem:testdb <br>

http://localhost:9090/account-api/h2-console <br>
http://localhost:9080/transaction-api/h2-console

##### SAMPLE CUSTOMER
![image](https://user-images.githubusercontent.com/4302984/233875915-19744b79-f760-4468-9457-b6ab84131574.png)


##### HOW TO RUN
The applications can be run with docker-compose <br>

###### Step 1 

run the maven command each project
```
$ mvn clean install
```
###### Step 2 
run compose up command
```
$ docker-compose up -d
``` 
##### ENDPOINTS & USAGE
The applications endpoints can be accessed via curl or Swagger UI
###### Create Account

```
curl -X 'POST' \
  'http://localhost:9090/account-api/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "customerId":1,
  "initialCredit": 15.99,
  "accountType": "CURRENT"
}'
```
###### Get Account
```
curl -X 'GET' \
  'http://localhost:9090/account-api/accounts/2' \
  -H 'accept: */*'
```
###### Add Balance
```
curl -X 'PATCH' \
  'http://localhost:9090/account-api/accounts/2' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "newBalance": 99.99
}'
```
###### Get UserInfo
```
curl -X 'GET' \
  'http://localhost:9090/account-api/customers/1' \
  -H 'accept: */*'
```

###### Create Transaction
```
curl -X 'POST' \
  'http://localhost:9080/transaction-api/transactions/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "accountId": 1,
  "amount": 45,
  "first": false
}'
```
###### Get Transaction
```
curl -X 'GET' \
  'http://localhost:9080/transaction-api/transactions/1' \
  -H 'accept: */*'
``` 
###### Get Account Transactions
```
curl -X 'GET' \
  'http://localhost:9080/transaction-api/transactions/accounts/1' \
  -H 'accept: */*'
``` 
