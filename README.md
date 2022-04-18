# Squad4J

## About

Squad4J is **actively seeking contributors**! 

## Using Squad4J

### Prerequisites

- Java 11 (or higher) runtime installed.

### Base Installation
If you wish to use Squad4J in its default configuration, simply download the [latest release]()

### Building Yourself (Required if you write plugins or make changes to Squad4J)
#### Additional Prerequisites
- Maven - Must be able to use Maven through the command-line with the `mvn` command or through your IDE.

## Configuring Squad4J

## Plugins
The following plugins are built into Squad4J.

Interested in coding your own plugin? See [Creating Your Own Plugins](./src/main/java/plugins/README.md) for more.


## Notes from Developer

## Versions & Releases

## Credits

- [SquadJS and Contributors](https://github.com/Thomas-Smyth/SquadJS) - The original Squad log parsing and server-side 
scripting framework. Paved the path for a lot of techniques that Squad4J uses to collect server
info and methods to interact with servers and players.

## Dependencies Used

- [org.json](https://mvnrepository.com/artifact/org.json/json/20210307) - Used for parsing JSON data.
- [mysql-connector-java](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.27) - Used for reading/writing to MySQL databases.
- [logback-classic](https://mvnrepository.com/artifact/ch.qos.logback/logback-classic/1.2.11) - Used for logging.
- [slf4j-api](https://mvnrepository.com/artifact/org.slf4j/slf4j-api/1.7.36) - Native implementation with logback.
- [commons-io](https://mvnrepository.com/artifact/commons-io/commons-io/2.11.0) - Library for I/O operations.
- [commons-cli](https://mvnrepository.com/artifact/commons-cli/commons-cli/1.5.0) - Library for CLI operations.
- [json-path](https://mvnrepository.com/artifact/com.jayway.jsonpath/json-path/2.7.0) - Used for providing JSON paths to evaluate config.json.
- [guava](https://mvnrepository.com/artifact/com.google.guava/guava/31.1-jre) - Various operations and collections.

## Built With

- [Maven](https://maven.apache.org/) - Dependency management

## License

```
Boost Software License - Version 1.0 - August 17th, 2003

Copyright (c) Robert Engle 2022

Permission is hereby granted, free of charge, to any person or organization
obtaining a copy of the software and accompanying documentation covered by
this license (the "Software") to use, reproduce, display, distribute,
execute, and transmit the Software, and to prepare derivative works of the
Software, and to permit third-parties to whom the Software is furnished to
do so, all subject to the following:

The copyright notices in the Software and this entire statement, including
the above license grant, this restriction and the following disclaimer,
must be included in all copies of the Software, in whole or in part, and
all derivative works of the Software, unless such copies or derivative
works are solely in the form of machine-executable object code generated by
a source language processor.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO EVENT
SHALL THE COPYRIGHT HOLDERS OR ANYONE DISTRIBUTING THE SOFTWARE BE LIABLE
FOR ANY DAMAGES OR OTHER LIABILITY, WHETHER IN CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.
```