<div align="center">

# 🌤️ Weather App

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

[![License](https://img.shields.io/badge/License-MIT-green.svg?style=flat-square)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen.svg?style=flat-square)]()

<br>

**A robust Java-based web application providing real-time weather updates and forecasts.**
Built with Jakarta EE (Servlet/JSP) and Maven, powered by the OpenWeatherMap API.

[Report Bug](https://github.com/Prathemsh-Coder/WheatherApp/issues) · [Request Feature](https://github.com/Prathemsh-Coder/WheatherApp/issues)

</div>

---

## 🚀 Features

- **📍 Auto-Location Detection**: Automatically detects your current location to display local weather instantly.
- **🔍 City Search**: Search for weather conditions in any city worldwide.
- **🌡️ Real-Time Data**: Get up-to-the-minute temperature, humidity, wind speed, and weather conditions.
- **📅 5-Day Forecast**: Plan ahead with a detailed 5-day weather prediction.
- **📱 Responsive Design**: A clean, modern interface that works beautifully on desktop and mobile.

## 🛠️ Technology Stack

| Component | Technology |
|-----------|------------|
| **Backend** | Java 1.8, Jakarta Servlet 6.0, Jakarta JSTL 3.0 |
| **Frontend** | HTML5, CSS3, JavaScript, JSP |
| **Build Tool** | Apache Maven |
| **API** | OpenWeatherMap API |
| **Server** | Apache Tomcat 10.1+ (Recommended) |

## ⚙️ Setup & Installation

Follow these steps to get the project running on your local machine.

### Prerequisites

*   Java Development Kit (JDK) 1.8+
*   Apache Maven
*   Apache Tomcat 10.1+
*   [OpenWeatherMap API Key](https://openweathermap.org/api)

### Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/Prathemsh-Coder/WheatherApp.git
    cd WheatherApp
    ```

2.  **Configure API Key**
    *   Rename the example properties file:
        ```bash
        cp src/main/resources/application.properties.example src/main/resources/application.properties
        ```
    *   Open `src/main/resources/application.properties` and paste your API key:
        ```properties
        api.key=YOUR_ACTUAL_API_KEY_HERE
        api.url=https://api.openweathermap.org/data/2.5/weather
        ```

3.  **Build the Project**
    ```bash
    mvn clean package
    ```

4.  **Run the Application**
    *   Copy the generated WAR file (`target/weather-app.war`) to your Tomcat `webapps` folder.
    *   Start Tomcat and visit: `http://localhost:8080/weather-app`

## 📂 Project Structure

```
weather-app/
├── src/
│   ├── main/
│   │   ├── java/com/weather/   # Backend Logic (Servlets, Config)
│   │   ├── resources/          # Configuration (application.properties)
│   │   └── webapp/             # Frontend (JSP, CSS, JS)
│   └── test/                   # Unit Tests
├── pom.xml                     # Maven Dependencies
└── README.md                   # Project Documentation
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

<div align="center">
    Made with ❤️ by <a href="https://github.com/Prathemsh-Coder">Prathamesh Raut</a>
</div>
