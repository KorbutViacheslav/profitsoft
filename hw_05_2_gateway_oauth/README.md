# Gateway OAuth

* [Task issued](https://docs.google.com/document/d/1ImI1umqfAcfahbdV2MUPl37LHMvElQbmEpUORGzAdr8/edit#heading=h.arwffw3rrrrx) (Only for users
  who have access to the internship)
* A short video about how the application works [YouTube]()

---

## Application Description

This application is a microservices-based system enhanced with a security layer. 
It features a centralized gateway for accessing microservices, GitHub authentication, and authorization.
The application ensures secure access and user authentication, leveraging Spring Cloud Gateway.

---

## Features

- #### Gateway Configuration:
  * Centralized access to all microservices through a single base URL and port using Spring Cloud Gateway.
  * Optional HTTPS configuration for secure communication.

- #### Authentication and Authorization:
    * GitHub OAuth2 authentication to restrict access to authenticated users only.
    *  Integration options include manual configuration at the gateway level, a dedicated microservice using Spring Authorization Server.
- #### API Security:
    * API access control via gateway-level header validation or microservices-level token validation.
- #### User Profile Endpoint:
    * `/profile` endpoint returns the user's name using information from the GitHub ID token.
- #### Frontend Security Integration:
    * Frontend requests the /profile endpoint.
    *  If the endpoint returns a 401 error, the frontend prompts the user to log in.
    *  The login button redirects the user to the authorization server, which then redirects to GitHub for authentication.
    *  After successful authentication, the frontend reloads, requests the /profile endpoint again, and displays the user's data if the response is 200.

---

### Usage

1. **Start project from [hw_02_book_management_application](../hw_02_book_management_application/README.md)**
2. **Start project from [hw_05_1_email_sender](../hw_05_1_email_sender/README.md)**
3. Open project in the terminal within the folder containing docker-compose.yaml file and execute the following
   commands:
      ````
      docker-compose up --build
      ````
4. Run [app](../hw_05_2_gateway_oauth/src/main/java/org/profitsoft/hw_05_2_gateway_oauth/Hw052GatewayOauthApplication.java)

---

## Summary
This application demonstrates a secure, deployed microservices architecture with centralized gateway access,
GitHub OAuth2 authentication, and CI/CD integration.
It ensures secure user access and profile management through a streamlined and robust security layer. 
The project leverages Spring Cloud Gateway for centralized routing, Spring Authorization Server,
and integrates a responsive frontend that adapts based on the user's authentication status.