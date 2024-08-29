package pl.dolien.weatherService.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherForecast;
import pl.dolien.weatherService.services.WeatherForecastService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/windsurfing")
@RequiredArgsConstructor
public class WeatherForecastController {
    private final WeatherForecastService service;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/locations-for-windsurfing")
    public ResponseEntity<List<WeatherForecast>> getLocations() {
        List<WeatherForecast> bestLocations = service.toWeatherForecasts();
        return ResponseEntity.ok(bestLocations);
    }

    @GetMapping("/best-location")
    public ResponseEntity<Optional<LocationResponse>> getBestLocation(@RequestParam(required = false) String date) {
        Date parsedDate;

        if (date == null || date.isEmpty()) {
            parsedDate = new Date();
        } else {
            try {
                parsedDate = DATE_FORMAT.parse(date);
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body(null);
            }
        }

        Optional<LocationResponse> bestLocationForWindsurfing = Optional.ofNullable(service.getBestForecastForDate(parsedDate));

        return ResponseEntity.ok(bestLocationForWindsurfing);
    }
}
