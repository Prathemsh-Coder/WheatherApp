package com.weather.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WeatherConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = WeatherConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getApiKey() {
        return properties.getProperty("api.key");
    }

    public static String getApiUrl() {
        return properties.getProperty("api.url");
    }
}
