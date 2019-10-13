
## Telegram bot with RESTful management.

### Description
The application consist of two parts:  
- Telegram bot: provides recommendations and information about tourist attractions in the selected city;  
- REST API: allows you to manage data.  
### Getting started  
Minimum requirements: maven, jre 8+, IDE (optional).  
- download project in your local repository;
- set `custom.bot.name` and `custom.bot.token` in application.properties;  
- build .jar with maven;
- run application with your IDE or with a command line interpreter from a directory of .jar file  
(e.g. `java -Dfile.encoding=utf-8 -jar jar-name.jar`)...  
... bot and RESTful service are started!  
Database is initialized with 4 test entries (id: 1-4). 
### REST API  
Root URL: `localhost:8080/rest/cities`
#### Get all cities  
`URL` - root URL  
`Method` - GET  
`Success response` - 200 OK. Content (example):  
[{"id": 1,"name": "City 1","description": "Description 1"}, {"id": 2,"name": "City 2","description":"Description 2"}]
#### Create city  
`URL` - root URL  
`Method` - POST  
`Data params` - {"name": "[string_notEmpty_notNull_unique]","description": "[string_notNull_sizeFrom5To1000]"}  
`Headers` - Content-Type = application/json  
`Success response` - 201 Created. Content (example):  
{"id": 5,"name": "city_name","description": "city_description"}  
`Error response` - 409 Conflict (unique constraint violation), 422 Unprocessable Entity (validation errors)  
#### Update city  
`URL` - /id  
`Method` PUT  
`Data params` - 
{"id":[id_equals_to_url_path_variable],"name": "[string_notEmpty_notNull_unique]","description": "[string_notNull_sizeFrom5To1000]"}  
`Headers` - Content-Type = application/json  
`Success response` - 204 No Content.  
`Error response` - 400 Bad Request (Mismatch id or id is not presented in a body),
 422 Unprocessable Entity (validation errors, not found entity)  
#### Delete city  
`URL` - /id  
`Method` DELETE  
`Success response` - 204 No Content.  
`Error response` - 422 Unprocessable Entity (not found entity)
 