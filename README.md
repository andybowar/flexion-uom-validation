Running the application:
===
* After cloning the repo, if using Intellij, go to the Project Structure settings and make sure OpenJDK 19 is set as the SDK.
* In the command line, navigate to the directory of this project and run `mvn clean install`
* Run the project by running `mvn spring-boot:run` in the command line

Using the application
---
#### This application features the ability to evaluate a student's guess as to the conversion of one unit of measure to another (e.g. 0 Celsius converts to 32 Fahrenheit)
* The application has one endpoint which can be tested in Swagger at http://localhost:8080/swagger-ui/index.html
* The application also has a basic web page which can be found [here](./scripts/front-end/index.html).