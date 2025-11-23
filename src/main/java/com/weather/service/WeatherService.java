package com.weather.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.weather.util.WeatherConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherService {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String GEO_URL = "http://api.openweathermap.org/geo/1.0/direct";

    public JsonObject getWeather(String city) throws IOException {
        String url = String.format("%sweather?q=%s&appid=%s&units=metric", BASE_URL, encode(city), WeatherConfig.getApiKey());
        return fetchJson(url);
    }

    public JsonObject getWeather(double lat, double lon) throws IOException {
        String url = String.format(java.util.Locale.US, "%sweather?lat=%f&lon=%f&appid=%s&units=metric", BASE_URL, lat, lon, WeatherConfig.getApiKey());
        return fetchJson(url);
    }

    public JsonObject getForecast(String city) throws IOException {
        String url = String.format("%sforecast?q=%s&appid=%s&units=metric", BASE_URL, encode(city), WeatherConfig.getApiKey());
        return fetchJson(url);
    }

    public JsonObject getForecast(double lat, double lon) throws IOException {
        String url = String.format(java.util.Locale.US, "%sforecast?lat=%f&lon=%f&appid=%s&units=metric", BASE_URL, lat, lon, WeatherConfig.getApiKey());
        return fetchJson(url);
    }

    public JsonArray searchCities(String query) throws IOException {
        String url = String.format("%s?q=%s&limit=5&appid=%s", GEO_URL, encode(query), WeatherConfig.getApiKey());
        String response = fetchString(url);
        return new Gson().fromJson(response, JsonArray.class);
    }

    public String getCityName(double lat, double lon) throws IOException {
        // Use OpenStreetMap Nominatim API for better accuracy
        String url = String.format(java.util.Locale.US, "https://nominatim.openstreetmap.org/reverse?format=json&lat=%f&lon=%f&zoom=10", lat, lon);
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            // Nominatim requires a User-Agent identifying the application
            request.addHeader("User-Agent", "WeatherApp/1.0");
            
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                JsonObject result = new Gson().fromJson(jsonResponse, JsonObject.class);
                
                if (result.has("address")) {
                    JsonObject address = result.getAsJsonObject("address");
                    // Try to find the most specific name available
                    if (address.has("city")) return address.get("city").getAsString();
                    if (address.has("town")) return address.get("town").getAsString();
                    if (address.has("village")) return address.get("village").getAsString();
                    if (address.has("county")) return address.get("county").getAsString();
                    if (address.has("state_district")) return address.get("state_district").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return value;
        }
    }

    private JsonObject fetchJson(String url) throws IOException {
        String response = fetchString(url);
        return new Gson().fromJson(response, JsonObject.class);
    }

    private String fetchString(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                return EntityUtils.toString(response.getEntity());
            }
        }
    }
}
