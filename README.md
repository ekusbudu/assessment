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
http://localhost:9090/account-api/h2-console/ <br>
http://localhost:9080/transaction-api/h2-console


##### HOW TO RUN
The applications can be run with docker-compose <br>

###### Step 1 

  run the maven command each project
  
  mvn clean install
  
###### Step 2 

  run docker-compose up -d
  
##### ENDPOINTS & USAGE
The applications endpoints can be accessed via curl or Swagger UI
###### Create Account

curl -X 'POST' \
  'http://localhost:9090/account-api/accounts' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "customerId":1,
  "initialCredit": 15.99,
  "accountType": "CURRENT"
}'

###### Get Account

curl -X 'GET' \
  'http://localhost:9090/account-api/accounts/2' \
  -H 'accept: */*'
  
###### Add Balance
curl -X 'PATCH' \
  'http://localhost:9090/account-api/accounts/2' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "newBalance": 99.99
}'

###### Get UserInfo

curl -X 'GET' \
  'http://localhost:9090/account-api/customers/1' \
  -H 'accept: */*'
