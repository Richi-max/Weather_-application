package com.API1.APP1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final String API_KEY = "7b249e24d1396b4322ff4fd9a8b98e57"; // Your actual API key
    private final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/weather")
    public void getWeather(@RequestParam String city, Model model) {
        String url = API_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

        try {
            String response = restTemplate.getForObject(url, String.class);
            logger.info("Weather data retrieved for city: {}", city);
            logger.debug("Weather response: {}", response);

            // Deserialize response to a Java object
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherResponse weatherResponse = objectMapper.readValue(response, WeatherResponse.class);
            model.addAttribute("weatherResponse", weatherResponse);
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error while fetching weather data for city: {}", city, e);
            model.addAttribute("error", "Error fetching weather data: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while fetching weather data for city: {}", city, e);
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
        }
    }
}

