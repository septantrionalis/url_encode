# url_encode

This project will shorten a URL as described in the assignment.<BR>
<BR>
Design decisions :
- Repository class doesn't goto a DB. Will use a HashMap to similate.
- If the user asks to shorten the same URL, the API will not regenerate the shortened URL but will return the old one.
- The shortened host is reserved. The user can't pass in a shortened host into the encode API to try and shorten it again.
# Sample Curl Statements:

### Encode:

curl --location 'localhost:8080/encode?url=https%3A%2F%2Fexample.com%2Flibrary%2Freact'<BR>

### Decode:

curl --location 'localhost:8080/decode?url=https%3A%2F%2Fshort.est%2Feu5PXE'<BR>

### Sample Reponse:
```
{
    "shortenedUrl": "https://short.est/eu5PXE",
    "normalUrl": "https://example.com/library/react"
}
```


# Sample curl statements for debug REST API:

### Add key:

curl --location --request POST 'localhost:8080/add?key=GeAi9K&normal-url=https%3A%2F%2Fexample.com%2Flibrary%2Freact'

### Get all:

curl --location 'localhost:8080/get'


