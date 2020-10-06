# Job Portal API Documentation

## /auth - Endpoint for user authentication and signup

### [POST] /auth/signin - Authenticates a user
#### Curl Command
```sh
curl -X POST "http://localhost:8080/auth/signin" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"password\": \"12345678\", \"username\": \"user\"}"
```
#### API Output
![auth signin](images/auth_signin.png)

### [POST] /auth/signup - Registers a user
#### Curl Command
```sh
curl -X POST "http://localhost:8080/auth/signup" -H "accept: application/json" -H "Content-Type: application/json" -d "{ \"email\": \"john@example.com\", \"password\": \"12345678\", \"roles\": [ \"recruiter\" ], \"username\": \"john\"}"
```
#### API Output
![auth signup](images/auth_signup.png)


## /jobs - Endpoints for getting and posting jobs

### [GET] /job - Gets the list of jobs based on query parameters
#### Curl Command
```sh
curl -X GET "http://localhost:8080/jobs?location=Remote&skills=Java%2C%20C%2B%2B%2C%20Python" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWNydWl0ZXIiLCJpYXQiOjE2MDE5ODAwMTIsImV4cCI6MTYwMjA2NjQxMn0.vuCrSLNX4ED0KPCEwferfKahEL_vr2OBnBK4uww4nNK1mFNWc1KD_DnUwjA7IoYiQCr3u6DrPmLCarZXECgo7g"
```
#### API Output
![jobs get](images/jobs_get.png)

### [POST] /job - Post a new job.
#### Curl Command
```sh
curl -X POST "http://localhost:8080/jobs" -H "accept: application/json" -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyZWNydWl0ZXIiLCJpYXQiOjE2MDE5ODAwMTIsImV4cCI6MTYwMjA2NjQxMn0.vuCrSLNX4ED0KPCEwferfKahEL_vr2OBnBK4uww4nNK1mFNWc1KD_DnUwjA7IoYiQCr3u6DrPmLCarZXECgo7g" -H "Content-Type: application/json" -d "{ \"company\": \"E Corp.\", \"expiresAfter\": 10368000, \"jobDescription\": \"Engineer, design and deploy solutions that will help Evil Corp. beat FSociety\", \"jobTitle\": \"Computer Security Specialist\", \"location\": \"New York\", \"skills\": [ \"Hacking\", \"Blue Team Skills\", \"Red Team Skills\" ]}"
```
#### API Output
![jobs post](images/jobs_post.png)