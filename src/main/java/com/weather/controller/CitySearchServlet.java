package com.weather.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.weather.service.WeatherService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/search")
public class CitySearchServlet extends HttpServlet {

    private WeatherService weatherService = new WeatherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("q");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (query == null || query.trim().length() < 3) {
            resp.getWriter().write("[]");
            return;
        }

        try {
            JsonArray results = weatherService.searchCities(query);
            resp.getWriter().write(results.toString());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
