# About
Voting system for deciding where to have lunch.

# Requirements
Design and implement a REST API (without frontend).

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

# Technologies
Some of technologies used in the project:
1. Spring Boot
1. Spring Security
1. Spring Rest Services
1. JPA, Hibernate, Spring Data JPA
1. Spring Data REST
1. Ehcache
1. H2 database
1. AssertJ, MockMvc

# Running the application
## Prerequisites
- Maven binary in your `PATH` variable
- `JAVA_HOME` variable set to JDK 1.8 or higher

## Running
`git clone <this repository>` or download in any other way  
`cd lunch-voting`  
`mvn spring-boot:run`

## Configuration
By default the application will start on port 8080. To start on different port set `server.port` property value.  
According to requirements, the application behaves differently before and after 11:00.  
Different time can be set using `cyclone.lunchvoting.voting-ends` property value (`HH-mm` format).

Example of running application on port 8085 with voting end time at 20:00:

`mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8085,--cyclone.lunchvoting.voting-ends=20-00`

# API
## curl
Application API is demonstrated using `curl` tool.

Windows and *nix curl commands may have different formats.  
When sending JSON in *nix system the parameter will be  
`-d '{"name":"Another brave world"}'`  
which is equivalent to Windows  
`-d "{\"name\":\"Another brave world\"}"`

All commands in this doc are in Windows format.

## Authorization

All HTTP endpoints in the application are protected, so it is mandatory to include security parameters for authorization.  
You can use one of two formats:
- administrator role: `-H "Authorization: Basic YWRtaW4xQG1haWwub3JnOmFkbWlucGFzcw=="` or `-u admin1@mail.org:adminpass`
- user role: `-H "Authorization: Basic dXNlcjFAbWFpbC5vcmc6dXNlcnBhc3M="` or `-u user1@mail.org:userpass`

Where `YWRtaW4xQG1haWwub3JnOmFkbWlucGFzcw==` is base64 encoded string `admin1@mail.org:adminpass`.


List of available users (login:password):
- admin1@mail.org:adminpass (role ADMIN)
- admin-o-user@mail.org:adminpass (roles ADMIN and USER)
- user1@mail.org:userpass (role USER)
- user2@mail.org:userpass (role USER)
- user3@mail.org:userpass (role USER)
- user4@mail.org:userpass (role USER)


## Commands
Assuming the application is running as root of http://localhost:8080/.

`/api/admin/restaurants` and `/api/admin/menu` endpoints support standard Spring Data REST parameters for paging and sorting which are:
- page: The page number to access (0 indexed, defaults to 0).
- size: The page size requested (defaults to 20).
- sort: A collection of sort directives in the format ($propertyname,)+[asc|desc]?.

Check full documentation at https://docs.spring.io/spring-data/rest/docs/current/reference/html/.

### List restaurants
`curl http://localhost:8080/api/admin/restaurants -u admin1@mail.org:adminpass`

### Get specific restaurant (ID 1)
`curl http://localhost:8080/api/admin/restaurants/1 -u admin1@mail.org:adminpass`

### Find restaurants by name containing string "pork" ignoring case
`curl http://localhost:8080/api/admin/restaurants/search/findByName?name=pork -u admin1@mail.org:adminpass -i`

### List all menu items
`curl http://localhost:8080/api/admin/menu -u admin1@mail.org:adminpass`

### Sort all menu items by price
`curl http://localhost:8080/api/admin/menu?sort=price,desc -u admin1@mail.org:adminpass -i`

### Get specific restaurant (ID 1) menu
`curl http://localhost:8080/api/admin/restaurants/1/menu -u admin1@mail.org:adminpass -i`

### Create new restaurant

`curl http://localhost:8080/api/admin/restaurants -X POST -u admin1@mail.org:adminpass -H "Content-Type:application/json" -d "{\"name\":\"Brave new world\"}" -i`

### Create menu in new restaurant (assuming generated ID is 4)
`curl http://localhost:8080/api/admin/menu -X POST -u admin1@mail.org:adminpass -H "Content-Type:application/json" -d "{\"name\":\"Vikings salad\", \"price\": \"85.50\", \"restaurant\": \"http://localhost:8080/api/admin/restaurants/4\"}" -i`

`curl http://localhost:8080/api/admin/menu -X POST -u admin1@mail.org:adminpass -H "Content-Type:application/json" -d "{\"name\":\"Firstborn carrot\", \"price\": \"10\", \"restaurant\": \"http://localhost:8080/api/admin/restaurants/4\"}" -i`

### Edit created menu item (assuming last generated ID is 25):
`curl http://localhost:8080/api/admin/menu/25 -X PUT -u admin1@mail.org:adminpass -H "Content-Type:application/json" -d "{\"id\":25, \"name\":\"Firstborn carrot BIG\", \"price\": \"100\", \"restaurant\": \"http://localhost:8080/api/admin/restaurants/4\"}" -i`

### Get user voting choice
`curl http://localhost:8080/api/user/list -u user1@mail.org:userpass -i`

### Vote for restaurant ID 4 by user1 (changed his mind) and user4 (new vote)
New votes will be accepted while voting is not finished (11:00 by default). Otherwise see exception handling part.

`curl http://localhost:8080/api/user/vote/4 -X POST -u user1@mail.org:userpass -i`

`curl http://localhost:8080/api/user/vote/4 -X POST -u user4@mail.org:userpass -i`

## Exception handling

### Vote for nonexistent restaurant
Expect HTTP status 404 with message "Restaurant ID 999 not found." (assuming restaurant ID 999 doesn't exist).

`curl http://localhost:8080/api/user/vote/999 -X POST -u user1@mail.org:userpass -i`

### Vote when voting is finished
Expect HTTP status 422 with message "Voting is not active at <datetime>" after voting is finished (11:00 by default).

`curl http://localhost:8080/api/user/vote/1 -X POST -u user1@mail.org:userpass -i`

## Extra
Some additional methods.

### Get current voting status
`curl http://localhost:8080/api/user/voting-status -u user1@mail.org:userpass -i`

### Get voting result
You can get voting result after voting is finished (11:00 by default). Otherwise see next command.

`curl http://localhost:8080/api/user/voting-result -u user1@mail.org:userpass -i`

### Get voting result when voting is not finished
Expect HTTP status 422 with message "Voting is not finished at <datetime>" after voting is finished (11:00 by default).

`curl http://localhost:8080/api/user/voting-result -u user1@mail.org:userpass -i`
