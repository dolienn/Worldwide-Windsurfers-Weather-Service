package pl.dolien.weatherService.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.services.WeatherService;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api/windsurfing")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService service;

    @GetMapping("/locations")
    public List<WeatherForecastDTO> getSuitableWeatherForecasts() {
        return service.getSuitableWeatherForecasts();
    }

    @GetMapping("/best-location")
    public Optional<LocationResponse> getBestForecast(@RequestParam(required = false) String date) {
        return ofNullable(service.getBestForecastForDate(date));
    }
}
