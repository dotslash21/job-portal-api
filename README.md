# Job Portal API Backend  
  
A simple Job Portal API backend created using Spring Boot.  
  
## Getting Started  
  
### Setting Up The Environment  
  
To run this project you'll need to ensure that the following software is installed in your system.  

- JDK 11
- Gradle 6.6.1 (Or you can use the provided gradle wrapper instead)
- MongoDB 4.4.1

### Building The Project

```
./gradlew clean build
```

### Starting The Server

```
./gradlew bootRun
```

## API Documentation

I've used Swagger to generate API documentation. It's much more interactive and intuitive than any
textual documentation.

To view the documentation, start the server and browse to `http://host:port/swagger-ui.html`.

If for some reason you are not able to view the swagger documentation, you can view the markdown
version [here](docs/api/README.md).

## Authors  
  
- Arunangshu Biswas ([arunangshubsws@gmail.com](mailto:arunangshubsws@gmail.com))