# url_encode

This project will shorten a URL as described in the assignment.<BR>
Credits: Ron Kinney (ronkinney@gmail.com)<BR>
<BR>
Design decisions :
- Repository class doesn't go to a DB. Will use a HashMap to simulate.
- If the user asks to shorten the same URL, the API will not regenerate the shortened URL but will return the old one.
- The shortened host is reserved. The user can't pass in a shortened host into the encode API to try and shorten it again.

# Running

This is a Spring Boot application. The starting point is "org.tdod.demo6.Demo6Application" and uses the typical RESTful API stack.<BR>
- DencoderServiceImpl in the service package handles the service calls and the app business logic.
- DencoderRepositoryImpl in the repository package handles and "DB" related calls.
- DencoderException in the exception package handles user case exceptions.
- DencocderEntity in the entity package is your POJO that is returned to the end user in JSON format.
- test folder contains the unit tests for Demo6Application and is called "Demo6ApplicationTests"

Bring the application up in a Spring Boot environment.  You should be able to clone the project, open it up in IntelliJ and run it.
The following curl statements can be run once the app is up:

# Sample Curl Statements:

### Encode:

curl --location 'localhost:8080/encode?url=https%3A%2F%2Fexample.com%2Flibrary%2Freact'<BR>

### Decode:

curl --location 'localhost:8080/decode?url=https%3A%2F%2Fshort.est%2Feu5PXE'<BR>

### Sample Valid Response:
```
{
    "shortenedUrl": "https://short.est/eu5PXE",
    "normalUrl": "https://example.com/library/react"
}
```

### Sample Invalid Response:
```
{
    "timestamp": "2024-07-16T18:24:53.709+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Shortened version of URL not found.",
    "path": "/decode"
}
```

# Sample curl statements for debugging the REST API.

These endpoints weren't part of the assignment and should not be surfaced to the end user.
They are used for testing.

### Add key:

curl --location --request POST 'localhost:8080/add?key=GeAi9K&normal-url=https%3A%2F%2Fexample.com%2Flibrary%2Freact'

### Get all:

curl --location 'localhost:8080/get'


