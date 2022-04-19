<div align="center">
<h1>Squad4J</h1>

<a href="https://github.com/roengle/Squad4J/releases"><img src="https://img.shields.io/github/v/release/roengle/squad4j?sort=semver" alt="Latest Release"></a>
<a href="https://github.com/roengle/Squad4J/graphs/contributors"><img src="https://img.shields.io/github/contributors/roengle/squad4j?color=green" alt="Contributors"></a>
<a href="https://github.com/roengle/Squad4J/blob/development/LICENSE.md"><img src="https://img.shields.io/github/license/roengle/Squad4J" alt="License"></a>

<br>
<a href="https://github.com/roengle/Squad4J/issues"><img src="https://img.shields.io/github/issues/roengle/Squad4J" alt="Github Issues"></a>
<a href="https://discord.gg/DjrpPuw"><img src="https://img.shields.io/discord/266210223406972928?label=Support&logo=Discord" alt="Discord Server"></a>

</div>

## About

Squad4J is **currently in development** and is **actively seeking contributors**! To contribute

Squad4J is a plugin framework written in Java designed to run alongside Squad servers. It draws inspiration 
from [SquadJS](https://github.com/Team-Silver-Sphere/SquadJS), which was the first framework to successfully
parse Squad log files to express the state of a server and its players in code.

Since Squad4J is in *development* a lot of sections of this README and code as a whole are **unfinished**.

## Using Squad4J

Squad4J currently only supports reading log files locally, so it must be run on the same server as your Squad server.

Squad4J requires access to the following for your server:
- Ability to read `SquadGame.log` file, usually located in `SquadGame/Saved/Logs`.
- Ability to connect to RCON running on your server (default TCP port 21114).
  - **If** you have multiple RCON connections to your server, ensure that `MaxConnections` in `Rcon.cfg` is set high enough to accommodate all connections. By default, Squad servers allow 5 simultaneous connections.
- Ability to query server (default UDP 27165).

If you utilize a firewall, ensure it opens ports to where Squad4J is running.
### Prerequisites

- Java 11 (or higher) runtime installed.

### Base Installation
This section is WIP.

### Building Yourself (Required if you write plugins or make changes to Squad4J)
#### Additional Prerequisites
- Maven - Must be able to use Maven through the command-line with the `mvn` command or through your IDE.

## Configuring Squad4J

## Plugins
Interested in coding your own plugin? See [Creating Your Own Plugins](./src/main/java/plugins/README.md) for more.

The following plugins are built into Squad4J. See below for how to configure them:

<details>
        <summary>ChatCommands</summary>
        <h2>ChatCommands</h2>
        <p>Implementation of ChatCommands from <a href="https://github.com/Team-Silver-Sphere/SquadJS#chatcommands">SquadJS</a>. ChatCommands can be configured to warn the user calling a specified command, or broadcast a message to the whole server based on a whole command.</p>
        <p><b>NOTE:</b> If you configure a command to broadcast to the whole server, it is recommended that the command can only execute from admin chat. To do this, configure the <code>ignoreChats</code> field like such: <code>["ChatAll", "ChatTeam", "ChatSquad"]</code>. This will only allow the command to be executed from <code>ChatAdmin</code>.</p>
        <h3>Options</h3>
        <ul>
        <li>
            <h4>prefix</h4>
            <h6>Description</h6>
            <p>A <b>string</b> which denotes the prefix for all commands. For example commands with names <code>!nextmap</code> and <code>!squad4j</code> should have <code>prefix</code> set to <code>!</code>.</p>
        </li>
        <h6>Default</h6>
        <code>"prefix" : "!"</code>
        <li>
            <h4>commands</h4>
            <h6>Description</h6>
            <p>An <b>array of objects</b> containing the following properties: 
                <ul>
                    <li><code>command</code> - The command that initiates the message.</li>
                    <li><code>type</code> - Either <code>warn</code> or <code>broadcast</code>.</li>
                    <li><code>response</code> - The message to respond with.</li>
                    <li><code>ignoreChats</code> - An array of strings of chat types to ignore. Valid values are <code>ChatAll</code>, <code>ChatTeam</code>, <code>ChatSquad</code>, and <code>ChatAdmin</code>. Other values are simply ignored.</li>
                </ul>
            </p>
            <h6>Default</h6>
        </li>
        <pre>
<code>"options":
[
    {
        "command": "squad4j",
        "type": "warn",
        "response": "This server is running Squad4J.",
        "ignoreChats": []
    }
]</code>
        </pre>
        </ul>
</details>

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