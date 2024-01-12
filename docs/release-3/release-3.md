# Release 3

## Table of Contents

- [Introduction to Release 3](#introduction-to-release-3)
- [Userstories](#userstories)
- [Implementation of Web Application](#implementation-of-web-application)
- [Implementation of API and Server](#implementation-of-api-and-server)
- [Updating and refactoring UI, Json and Core modules](#updating-and-refactoring-ui-json-and-core-modules)
- [Tests](#tests)
- [Diagrams](#diagrams)
- [Work Habits](#work-habits)
- [Checkstyle and Spotbugs](#checkstyle-and-spotbugs)
- [Regarding Eclipse Che and React](#regarding-eclipse-che-and-react)

## Introduction to Release 3

Release 3 implements a new frontend using React and an API using the REST standard. The focus for this release has been development of the new React client, the springboot server and updating the JavaFX application. Along with the above Release 3 features new tests and higher test coverage along with improved Javadoc and code readability.

The decision to implement our frontend in React was a result of wanting to achive a more modern visual design and getting acquainted with new technologies commonly used in the industry. The JavaFX application remains functionally and visually similar to release 2, but the code has been updated to work with then new API. More about this [here](#updating-and-refactoring-ui-json-and-core-modules).

To implement our API we used a springboot server containing all endpoints. This is located in the new springboot module. [springboot](/mixmaven/springboot/README.md)

## Userstories

The Userstories used for this release can be found [here.](/docs/release-3/user-stories.md)

## Implementation of Web Application

As mentioned in the introduction we have focused on creating a web application for MixMaven. Documentation for the web application can be found
[here.](/webapp/README.md)

The decision to move our focus over to developing a web application over further developing the java application was something the group discussed for some time. We came to the conclusion that a web application would allow us to get experience with working with a different development environment. Simultaneously the group agreed that technologies like React are very modern and largely industry standard, whereas javafx is far less utilised. We also believe that React will give us the tools to development a better client for the end user.

The web application has the exact same core functionality as the java application, but provides a much improved (UX) user experience.

We implemented better functionality for navigating the list of drinks on the home page:

- Each drink-card is by default collapsed to avoid unnecessarily cluttering the homepage
- The user can expand each individual card by simply clicking or just pressing "expand all". This reveals all relevant information like ingredients and alcohol content.
- The option to filter alcoholic / non-alcoholic drinks
- The list will by default sort in ascending order (A-Z), with the option to toggle with descending order.

Better feedback to the user in the new/edit drink page:

- Immediate feedback to the user through error messages on invalid inputs.
- The buttons for add ingredient and create drink is disabled until the required fields are filled and at least one ingredient is added.
- If the type of the ingredient is not of type alcohol, the option to add a alcohol percent is removed.

We also implemented a more humane and modern design:

- A softer and more delightful color scheme. Also color coded alcoholic/non-alcoholic drinks
- Less clutter in new drink, with dynamic input labels/placeholders
- Animations make the app feel more adaptive and responsive

The implementation of react-query:

- React query allows the server interaction to be more precise:

  - All the drinks are fetched when the app is first loaded.
  - Whenever we add/edit/delete a drink we update the drink cache in react query. This leads to the update showing instantly for the user without having to wait for a new fetch to the server. (this is more noticeable on a slow server)
  - We can invalidate the cache whenever we want to make sure it is synced with the server. This happens in the background and helps in the case of multiple users updating the same database.

- The combination of react query and the Statusmessage component will display a loading indicator or error message whenever the query to the server is not (yet) successful, keeping the user informed on what happens behind the scenes.

Images of the web application can be found in the webapp [README.md](/webapp/README.md) or in the [images](/docs/release-3/images) folder of release-3.

## Implementation of API and Server

Release 3 implements a new API adhering to the REST design principles. The server is implemented through Springboot and contains several endpoints which is documented [here.](/docs/release-3/API.md). The API is utilized by both the JavaFX and the React client.

For more information on our API se [API.md](./API.md)

Instructions for running the server together with;

- [Java Application](/mixmaven/README.md#build-and-running-the-project)
- [Web Application](/webapp/README.md#running-the-app)

## Updating and refactoring UI, Json and Core modules

Since the previous release we have made some substantial changes in the UI, Json and core modules. The goal has been to support the JavaFX working both locally and remotely in a similar manner to the webapp. Furthermore we wanted to make the code more modular such that the different layers in our application were clearly defined.

To illustrate this, release 2 worked by having each UI controller interacting directly with the DataHandler in the Json module. Release 3 solves this by having the MixMavenController interacting with a DataAccess interface implemented by DirectDataAccess and RemoteDataAccess. RemoteDataAccess interacts with the API while DirectDataAccess interacts with the DataHandler. The result is that the Json and UI module now only interact through the DataAccess interface where previously each controller were dependent on the Json module.

In the Core module we added a MixMavenModel Class which contains all of our application data and the necessary methods to add, get, replace and remove drinks.

To improve maintainability, readability and improve code quality, a lot of code has been overhauled in the three modules. Long methods have been shortened by using helper methods in order to follow the single responsibility principle and make the code more readable. For example the previously long initialize method in EditDrinkController is now merely 5 lines of method calls.

## Tests

### Why do we have tests

As the project has grown in complexity and scope, unit tests has been crucial in development. The tests have been instrumental while [updating and refactoring](#updating-and-refactoring-ui-json-and-core-modules) to ascertain that everything is still working as intended. To maintain high code quality in our codebase we have set high standards for testing all modules.

### Test coverage

Since release 2 test coverage has been substantially improved, as testing has been a priority. With the help of **JaCOCO** we have been able to analyze our test coverage and we now have an average test coverage of about 80% in all modules. We consider this satisfactory as the remaining code would be trivial to tests (methods like "setters" or "getters") or consist of edge cases like code resulting from an IOException.

#### Core

In the core module jacoco reports a test coverage of 84 %.

The core module is generally very simple in nature and is therefore very straightforward to test. We are very happy with the coverage in the core module. The largest part of the missing 16 % is from a constructor that exists to streamline a test.

#### Json

In the json module jacoco reports a test coverage of 78 % or 65 % depending on whether the user.home directory has been initialized or not.

The json module is somewhat difficult in nature to increase the test coverage on since a big part of the module is error handling with errors we are not able to reproduce in the tests.

#### Springboot

In the core module jacoco reports a test coverage of 81 %.

The springboot module is almost entirely tested with the biggest exception being the testing of the **replace drink** endpoint, which we did not have the time to test thoroughly. This is a point where we as a group have to take critic.

### Ui

Testing of *ui* is done in with the help of TestFX where we have written parameterized tests where a the test manually clicks around and interacts with the app, filling fields and imitating an user.

Jacoco reports a test coverage of 84 %

The ui module is a module we are very happy with the coverage off. We believe all necessary code has been thoroughly tested. The remaining 16 % mainly consists of edge cases that appear in both editDrinkController.java  and addDrinkController.java and is tested in one of these.

We do recognise that we could have achieved higher test coverage. But we are certain that our current code coverage extends over most, if not all, user cases. It is naturally possible to improve upon the test coverage in the future.

### Webapp

For testing of the webapp we used Cypress for end to end testing. We chose cypress for it simplicity and also extensive support. It comes out of the box with an interactive view you can open in a browser (Run `npm run cy:open` inside the webapp folder). And the functions it comes with are easy to understand and extremely intuitive even though none of us had used it before.

Cypress allowed us to write tests simulating user behaviour for all the functionality implemented in the webapp. Inside [webapp/cypress](/webapp/cypress) you will find home_spec, add_drink_spec and edit_drink_spec. Files which test all the functionality on the associated pages by navigating around, inputing typical inputs and clicking all the buttons. This ensures that whenever we change the code we can run these tests to make sure that none of the functionalities unexpectedly breaks.

It is important to note that even though Cypress tests performs the tests on the actual webapp, it does not connect to our server. Our team made this decision since the development of the webapp and server happened in parallel to each other. This meant that the server might not be complete or function properly (yet) which would otherwise leave the client tests breaking and redundant until the completion of the server. We worked around this by intercepting all api calls the client made and faked a correct server response. We also made sure to check the requests made to make sure they are as expected. Now we had a isolated testing environment that we know for sure will work when the server is complete and sends the correct responses, without relying in the server.

The test coverage for the webapp is something we chose to exclude. Cypress does not come with the capabilities to to this out of the box. Even though there are posibilities to do this, it will require us to istrument our code and put in more work to achieve this. We simply did not prioritize that since it is not a requirement and we are already confident the tests cover most (if not all) of the code in the webapp.

## Diagrams

See [release-3/diagrams](./diagrams/)

## Work Habits

See [Work Habits Release 3](./work-habits-release-3.md)

## Checkstyle and Spotbugs

See [Release 2](/docs/release-2/release-2.md#checkstyle)

## Regarding Eclipse Che and React

We had issues with running react in eclipse che. Read more about this [here](/docs/release-3/challenges.md)
