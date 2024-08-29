package pl.dolien.weatherService.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherData;
import pl.dolien.weatherService.entities.WeatherForecast;
import pl.dolien.weatherService.services.WeatherForecastService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WeatherForecastControllerTest {
    @Mock
    private WeatherForecastService service;

    @InjectMocks
    private WeatherForecastController controller;

    private LocationResponse locationResponse;
    private WeatherForecast forecast;
    private List<WeatherForecast> forecastList;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        locationResponse = new LocationResponse("Jastarnia", 22.57, 12.34);
        forecast = new WeatherForecast();
        forecast.setLocation(new Location("Jastarnia", "Poland", "PL"));
        forecast.setData(List.of(new WeatherData(
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 15.0, 25.0, 15.0, 18.0
        )));

        forecastList = Collections.singletonList(forecast);
    }

    @Test
    void getLocations_Success() {
        when(service.toWeatherForecasts()).thenReturn(forecastList);

        ResponseEntity<List<WeatherForecast>> response = controller.getLocations();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Jastarnia", response.getBody().get(0).getLocation().getCityName());
    }

    @Test
    void getBestLocation_Success_WithValidDate() throws ParseException {
        String dateStr = "2024-08-29";
        Date date = DATE_FORMAT.parse(dateStr);
        when(service.getBestForecastForDate(date)).thenReturn(locationResponse);

        ResponseEntity<Optional<LocationResponse>> response = controller.getBestLocation(dateStr);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(Objects.requireNonNull(response.getBody()).isPresent());
        assertEquals("Jastarnia", response.getBody().get().getCityName());
    }

    @Test
    void getBestLocation_InvalidDateFormat() {
        ResponseEntity<Optional<LocationResponse>> response = controller.getBestLocation("invalid-date");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getBestLocation_EmptyForecast() throws ParseException {
        String dateStr = "2024-08-29";
        Date date = DATE_FORMAT.parse(dateStr);
        when(service.getBestForecastForDate(date)).thenReturn(null);

        ResponseEntity<Optional<LocationResponse>> response = controller.getBestLocation(dateStr);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isPresent());
    }
}
