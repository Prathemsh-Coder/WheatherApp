package com.weather.controller;

import com.google.gson.JsonObject;
import com.weather.service.WeatherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/weather")
public class WeatherServlet extends HttpServlet {

    private WeatherService weatherService = new WeatherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String city = req.getParameter("city");
        String latStr = req.getParameter("lat");
        String lonStr = req.getParameter("lon");

        try {
            JsonObject weatherData;
            JsonObject forecastData;

            if (latStr != null && lonStr != null && !latStr.isEmpty() && !lonStr.isEmpty()) {
                double lat = Double.parseDouble(latStr);
                double lon = Double.parseDouble(lonStr);
                weatherData = weatherService.getWeather(lat, lon);
                forecastData = weatherService.getForecast(lat, lon);
                
                // Get accurate city name using Reverse Geocoding
                String accurateCity = weatherService.getCityName(lat, lon);
                if (accurateCity != null) {
                    weatherData.addProperty("name", accurateCity);
                }
            } else {
                if (city == null || city.trim().isEmpty()) {
                    city = "London"; // Default
                }
                weatherData = weatherService.getWeather(city);
                forecastData = weatherService.getForecast(city);
            }
            
            if (weatherData.has("cod") && weatherData.get("cod").getAsInt() == 200) {
                // Current Weather
                req.setAttribute("city", weatherData.get("name").getAsString());
                req.setAttribute("country", weatherData.get("sys").getAsJsonObject().get("country").getAsString());
                req.setAttribute("temperature", Math.round(weatherData.get("main").getAsJsonObject().get("temp").getAsDouble()));
                req.setAttribute("description", weatherData.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
                req.setAttribute("main", weatherData.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString());
                req.setAttribute("humidity", weatherData.get("main").getAsJsonObject().get("humidity").getAsInt());
                req.setAttribute("windSpeed", weatherData.get("wind").getAsJsonObject().get("speed").getAsDouble());
                req.setAttribute("icon", weatherData.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("icon").getAsString());
                
                // Forecast
                com.google.gson.JsonArray jsonList = forecastData.getAsJsonArray("list");
                java.util.List<JsonObject> forecastList = new java.util.ArrayList<>();
                for (com.google.gson.JsonElement element : jsonList) {
                    forecastList.add(element.getAsJsonObject());
                }
                req.setAttribute("forecastList", forecastList);
            } else {
                req.setAttribute("error", "Location not found.");
            }
            
        } catch (Exception e) {
            req.setAttribute("error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
