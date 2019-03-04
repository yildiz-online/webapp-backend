# Yildiz-Engine webapp backend.

This is the official repository of the Web application, this application offer several possibilities, like creating an account, displaying news,...

## Features

* Account creation.
* News display.
* Email validation
* Does not require a container to run (spring-boot)
* Asynchronous requests
* ...

## Requirements

To build this module, you will need the latest JDK, and Maven 3.

## Coding Style and other information

Project website:
https://engine.yildiz-games.be

Issue tracker:
https://yildiz.atlassian.net

Wiki:
https://yildiz.atlassian.net/wiki

Quality report:
https://sonarcloud.io/dashboard?id=be.yildiz-games%3Ayildiz-webapp-backend

## License

All source code files are licensed under the permissive MIT license
(http://opensource.org/licenses/MIT) unless marked differently in a particular folder/file.

## Build instructions
Go to your root directory, where you POM file is located.

Then invoke maven

	mvn clean install

This will compile the source code, then run the unit tests, and finally build a jar file.

## Usage

In your maven project, add the dependency

```xml
<dependency>
    <groupId>be.yildiz-games</groupId>
    <artifactId>webapp-backend</artifactId>
    <version>LATEST</version>
</dependency>
```
Replace LATEST with the correct version.
## Contact
Owner of this repository: Grégory Van den Borre