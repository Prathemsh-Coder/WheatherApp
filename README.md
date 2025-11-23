# Weather App

![Java](https://img.shields.io/badge/Java-1.8-orange)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![License](https://img.shields.io/badge/License-MIT-green)
![Status](https://img.shields.io/badge/Status-Active-brightgreen)

A Java-based web application that provides real-time weather information using the OpenWeatherMap API. Built with Jakarta EE (Servlet/JSP) and Maven.

## Features

- **Current Weather**: Get real-time weather updates for any city.
- **5-Day Forecast**: View weather predictions for the next 5 days.
- **City Search**: Search for weather conditions in specific locations.
- **Auto-Location**: Automatically detects user location to show local weather.

## Technology Stack

- **Backend**: Java 1.8, Jakarta Servlet 6.0, Jakarta JSTL 3.0
- **Frontend**: HTML5, CSS3, JavaScript, JSP
- **Build Tool**: Maven
- **External API**: OpenWeatherMap

## Prerequisites

- Java Development Kit (JDK) 1.8 or higher
- Apache Maven
- Apache Tomcat 10.1+ (or any Jakarta EE 10 compatible server)
- OpenWeatherMap API Key

## Setup & Installation

1.  **Clone the repository**
    ```bash
    git clone <repository-url>
    cd weather-app
    ```

2.  **Configure API Key**
    - Copy the example properties file:
      ```bash
      cp src/main/resources/application.properties.example src/main/resources/application.properties
      ```
    - Open `src/main/resources/application.properties` and replace `YOUR_API_KEY_HERE` with your actual OpenWeatherMap API key.

3.  **Build the Project**
    ```bash
    mvn clean package
    ```

4.  **Run the Application**
    - Deploy the generated WAR file (`target/weather-app.war`) to your Tomcat server.
    - Or run locally if configured with a Maven plugin (e.g., Tomcat/Jetty).

## Project Structure

```
src/
├── main/
│   ├── java/           # Java source code (Servlets, Services, Utils)
│   ├── resources/      # Configuration files
│   └── webapp/         # Web resources (JSP, CSS, JS, WEB-INF)
└── test/               # Unit tests
```

## License

[MIT](LICENSE)
