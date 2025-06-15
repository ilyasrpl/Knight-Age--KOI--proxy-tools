# KOI Proxy Tools

## Description

KOI Proxy Tools is a network traffic snipping tool that intercepts and displays network packets. It presents the traffic in hexadecimal format, with two text areas:

*   Left: Client to server traffic
*   Right: Server to client traffic

The traffic is parsed into packets for easier analysis.

## Compilation

This project uses Apache Maven Daemon (mvnd) for building. To compile the project, use the following command:

```bash
mvnd clean install
```

This command will:

1.  `clean`: Remove any previously built artifacts.
2.  `install`: Compile the source code, run tests, and package the application.

## Testing

To run the tests, use the following command:

```bash
mvnd test
```

This command will execute the unit tests located in the `src/test/java` directory.

## Requirements

*   Apache Maven Daemon (mvnd) 1.0.2
*   Apache Maven 3.9.9
*   Java version 1.8.0\_202

## Environment

*   OS name: Windows 10
*   Architecture: amd64

## Default Proxy Servers

*   garuda hsindo20.teamobi.com:19130
*   indonaga hsindo.teamobi.com:19129
