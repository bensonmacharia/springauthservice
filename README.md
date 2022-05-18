## Spring Security
Spring Security comes in handy by providing a powerful, secure and customizable `authentication` and `authorization` framework for Spring Boot microservices. Spring Security implements a `Role Based Access Control (RBAC)` model that can help mitigate some inherent Authentication and Authorization security challenges such as violation of the least privilege principle, insecure direct object references and unauthorized privilege escalation.
## Authentication and Authorization
This repository contains source code for a simple implementation of Spring Security framework in Spring Boot.

The application flow is as illustrated below for authentication, authorization and generation of refresh token.
![Application Flow](https://bmacharia.com/wp-content/uploads/2022/05/combined_flow.png)
## Deployment
> - Clone the repository
> - Open in the folder your IDE
> - Edit the src/main/resources/application.properties file with you DB configuration
> - Run the application
> - Test the API endpoints using the downloaded postman collection
## References
> [Spring Boot Authentication and Authorization](https://bmacharia.com/2022/05/17/spring-boot-authentication-and-authorization/)

>  [Spring Security](https://spring.io/projects/spring-security)
