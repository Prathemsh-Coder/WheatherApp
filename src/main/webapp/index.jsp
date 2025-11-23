<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Weather App</title>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                <link rel="stylesheet" href="css/style.css">
                <script src="https://cdnjs.cloudflare.com/ajax/libs/lottie-web/5.9.6/lottie.min.js"></script>
            </head>

            <body>

                <div class="app-container">
                    <!-- Search Section -->
                    <div class="search-container">
                        <form action="weather" method="get" class="search-box" autocomplete="off">
                            <input type="text" id="city-input" name="city" placeholder="Search city..." value="${city}">
                            <input type="hidden" id="lat-input" name="lat">
                            <input type="hidden" id="lon-input" name="lon">
                            <button type="submit"><i class="fas fa-search"></i></button>
                            <div id="suggestions" class="suggestions-dropdown"></div>
                        </form>
                        <button id="location-btn" class="location-btn" title="Use Current Location">
                            <i class="fas fa-location-arrow"></i>
                        </button>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="error-message">
                            <i class="fas fa-exclamation-circle"></i> ${error}
                        </div>
                    </c:if>

                    <c:if test="${empty error}">
                        <!-- Main Weather Card -->
                        <div class="weather-card glass">
                            <div class="weather-header">
                                <div>
                                    <h1 class="city-name">${not empty city ? city : 'London'}<span
                                            class="country-badge">${country}</span></h1>
                                    <p class="date">
                                        <%= new java.text.SimpleDateFormat("EEEE, d MMMM").format(new java.util.Date())
                                            %>
                                    </p>
                                </div>
                                <div class="lottie-container" id="lottie-weather" data-weather="${main}"></div>
                            </div>

                            <div class="weather-body">
                                <div class="temp-box">
                                    <span class="temperature">${not empty temperature ? temperature : '--'}°</span>
                                    <p class="description">${not empty description ? description : 'Check the weather'}
                                    </p>
                                </div>

                                <div class="details-grid">
                                    <div class="detail-item">
                                        <i class="fas fa-tint"></i>
                                        <div class="detail-info">
                                            <span class="value">${not empty humidity ? humidity : '--'}%</span>
                                            <span class="label">Humidity</span>
                                        </div>
                                    </div>
                                    <div class="detail-item">
                                        <i class="fas fa-wind"></i>
                                        <div class="detail-info">
                                            <span class="value">${not empty windSpeed ? windSpeed : '--'} km/h</span>
                                            <span class="label">Wind</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- 5-Day Forecast -->
                        <c:if test="${not empty forecastList}">
                            <h2 class="section-title">5-Day Forecast</h2>
                            <div class="forecast-container">
                                <c:forEach items="${forecastList}" var="item" varStatus="loop">
                                    <%-- Show one item per day (approx every 8th item since api is 3-hour intervals)
                                        --%>
                                        <c:if test="${loop.index % 8 == 0}">
                                            <div class="forecast-card glass">
                                                <p class="forecast-date">
                                                    <fmt:parseDate value="${item.get('dt_txt').getAsString()}"
                                                        pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" pattern="EEE" />
                                                </p>
                                                <div class="forecast-icon">
                                                    <img src="http://openweathermap.org/img/wn/${item.get('weather').getAsJsonArray().get(0).getAsJsonObject().get('icon').getAsString()}.png"
                                                        alt="icon">
                                                </div>
                                                <p class="forecast-temp">
                                                    <fmt:formatNumber
                                                        value="${item.get('main').getAsJsonObject().get('temp').getAsDouble()}"
                                                        maxFractionDigits="0" />°
                                                </p>
                                            </div>
                                        </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:if>
                </div>

                <script src="js/script.js"></script>
            </body>

            </html>