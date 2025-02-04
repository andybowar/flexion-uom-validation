Running the application:
===
* Navigate to the directory of this project and run `./mvnw clean install`
* Run the project with `./mvnw spring-boot:run`

Using the application
---
#### This application features the ability to evaluate a student's guess as to the conversion of one unit of measure to another (e.g. 0 Celsius converts to 32 Fahrenheit)
* The application has one endpoint which can be tested in Swagger at http://localhost:8080/swagger-ui/index.html
* The application also has a basic front end which can be found [here](./scripts/front-end/index.html).
  * To load the FE, simply open index.html in a browser while the application is running

Notes on functionality
---
* On the webpage, the user is restricted to numbers only for the input numerical value. Interacting with the endpoint in Swagger has no such guardrails, so the user is able to send any values, including strings.
* Again on the webpage, the expected output for the "Response" field is "correct", "incorrect", or "invalid"
  * "invalid" should only occur when units of different types are being compared
  * An incorrect answer, including something nonsensical like a string, should render a response of "incorrect"

Pushing code and deploying
---
To push new code: 
* Create a new branch with `git checkout -b [new-branch-name]`
* `git add .`
* `git commit -m ["this commit will..."]` i.e. The commit message should complete the sentence, "This commit will..."
* `git push --set-upstream flexion-uom-valdiation <branch name>`
* In Github, create a new pull request to `main`. Once the branch is merged to main, a deploy to AWS will kick off.

Improvement Tasks
---
* Update pipeline configuration to include running all unit and integration tests before deploying
* The FE code currently sends a separate request for each row. This is not the most optimal method, so we could refactor the endpoint and service to take in a list of problems to evaluate.
* The volume conversion logic in UnitConversionUtil could be made more DRY
* Improve error handling throughout, such as providing more details from the API when something goes wrong
* More tests could be added to cover more cases with varying decimal places, as well as special characters
