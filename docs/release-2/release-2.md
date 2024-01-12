# Release 2

## Table of Contents

- [Changes to Persistence](#changes-to-persistence)
- [Architecture](#architecture)
- [Work Habits](#work-habits)
- [Tests](#tests)
- [Checkstyle](#checkstyle)
- [Spotbugs](#spotbugs)

## Changes to Persistence

### Persistence in Release 1

In release 1, our persistance consisted of a singular json file, *data.json*. Which was stored in the ui module. The persistance was assited with the dependency GSON. In release 2 we have moved on from this solution.

### Persistence in Release 2

The new solution consists of the app saving the data, still in a json fil, *data.json*, locally on the users system. This is primarily for privacy reasons such that the users data remains only visible to the users app and can not be accessed by third-parties. Secondary this solution makes it so that the app maintains a constant size. We are still utilizing GSON as a dependency for our persistence.

## Architecture

### Architecture in Release 1

In release 1, the app was multi-modular with 2 modules *ui* and *core*. Where *ui* was responsible for alle the visible elements, i.e. the fxml files, the fxml controllers and the *data.json* file itself. This is the parts of the app facing the user, the frontend. On the other hand *core* was responsible for all the logic of the app and the operation and logic of the persistence. This solution worked for the persistence as of release 1, but had to be changed for release 2.

### Architecture in Release 2

In the process of implementing the changes to persistence the group concluded that it would be appropriate to move to a multi-modular architecture with 3 modules, now including the *json* module. In this transition from a bimodular to a trimodular architecture, the *data.json* file was moved out of *ui* and the logic behind persistance was moved from *core* to *json*. We believe this is a far more optimal structure, since every module only has one objective in contrast to persistance being shared between *ui* and *core*.

The currenct architecture can be visually represented by the diagram below:

![image](../../mixmaven/architecture.png)

## Work Habits

### Planned Work Habits

As a group we had a clear plan in terms of how we wanted to work on this release. The majority (> 90%) of the programming and planning was done during bi-weekly work sessions. Lasting from between 4 to 8 hours depending on need.

### Structure of Work Sessions

During these sessions we had a clear structure. The sessions started with a quick meeting where we would go through what we had done, what we needed to do, solutions to issues members faced. The structure vaguely resembling scrum meetings. After this meeting we would split the work needed to be done, first by creating issues in gitlab and then using git branches where we would try to solve the gitlab issues. The programming itself was often executed in a two-pair configuration where two and two would cooperate on the same module (and/or) issue, utilising pair programming.

### Milestones

All the commits relating to release 2 has in gitlab been implemented under the milestone **Assignment 2**. The reason for utilising milestones was so that we would be able to gather all the work surrounding release 2. This has given us the opportunity to be able to view our progress.

 And the majority off branch merges correspond to gitlab issues. There were unfortunately a few merges that did not directly correspond to issues, this was often a result of individual mistakes were the group members would merge without fully implementing solutions to the issue.

### Merging of Branches

We utilised branches in order to work on gitlab issues one at the time. The majority off branch merges correspond to a specific gitlab issue. There were unfortunately a few merges that did not directly correspond to issues, this was often a result of individual mistakes where the group members would merge without fully implementing solutions to the issue.
Whenever a member had pushed their commits and created a merge request. The other group members would review the request, comment on possible errors or propose different solutions. This is something we initated so that every member would have to read each other's code such that everyone has a general understanding of the app's codebase. This habit worked for the most parts, but unfortunately during the more hectic work sessions we fell somewhat out of this. This is something we need to work on during the next release.

## Tests

### Why do we have tests

In order to verify the quality of our codebase we have wtritten substantial tests for all of the modules. The tests are there to make sure bugs and errors do not find their way into the codebase. Our general procedure regarding testing has been to continuously write tests when the app was expanded with further functionality.

### Test coverage

We have strived to maintain as great as possible test coverage, utilising the dependecy **JaCoCo** in order to assist us in analyzing our test coverage. This does not mean we have strived to have 100% test coverage. This is for the simple reason that we do not believe it is necessary to test every method. Basic methods like setters and getters are generally of such a simple nature that it would be unecessary to docomprehensive testing of them. Unfortunately we do recognise that our code coverage absolutely could have been better, and is something we are striving to improve.

## Testing of ui

Testing of *ui* is done in with the help of TestFX where we have written parameterized tests where a the test manually clicks around and interacts with the app, filling fields and imitating an user.

## Checkstyle

We utilise the dependecy Checkstyle to make sure our code keeps with with current standards for java code.

## Spotbugs

We utilise the dependecy Spotbugs in order to make sure our codebase does not contain possible bugs. Spotbugs analyses our code base for bug patterns and warns of possible solution that could could lead to anything form minimal to critical bugs.
