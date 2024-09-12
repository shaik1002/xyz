This is a sample Java project for TeamCity.

Tutorials that use this project:

* [TeamCity On-Premises](https://www.jetbrains.com/help/teamcity/create-pipeline.html)
* [TeamCity Cloud](https://www.jetbrains.com/help/teamcity/cloud/create-pipeline.html)
* [TeamCity Pipelines](https://www.jetbrains.com/help/teamcity/pipelines/tutorial-multi-job-pipeline.html)


## Dependencies and Requirements

* Java 11
* SpringBoot 2.7.18
* Gradle 7.6.2
* JUnit Jupiter 5.7.0
* Docker connection (optional, to push built images)

## Overview

Versioned settings stored in the `.teamcity` folder generate a TeamCity project with five build configurations:

* **TodoApp** — runs the Gradle step that builds this application.
* **TodoImage** — runs a custom script to build a Docker image using a Dockerfile from the `/docker` folder. Requires files produced by the **TodoApp** configuration (see the note below).
* **Test1** and **Test2** — configurations that execute Gradle steps to run tests from `/test1` and `/test2` folders.
* **TestReport** — a [composite build configuration](https://www.jetbrains.com/help/teamcity/composite-build-configuration.html) that does not perform any building tasks and serves as an entry point to launch the entire build chain.

> NOTE: The **TodoImage** build configuration does NOT have required dependencies on **TodoApp** to import required `todo.jar` and use it for building an image. These dependencies should be created manually to run the pipeline, see aforementioned tutorials for more details.
