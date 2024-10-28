package com.API1.APP1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    private final WeatherController weatherController;

    public WebController(WeatherController weatherController) {
        this.weatherController = weatherController;
    }

    @GetMapping("/")
    public String home() {
        return "index"; // Thymeleaf view name
    }

    @GetMapping("/weather-view")
    public String weatherView(@RequestParam String city, Model model) {
        weatherController.getWeather(city, model); // Directly pass the model
        return "weather"; // Thymeleaf view name for weather results
    }
}



