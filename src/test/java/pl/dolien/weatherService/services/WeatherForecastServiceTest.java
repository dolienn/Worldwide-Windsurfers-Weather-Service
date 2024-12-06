package pl.dolien.weatherService.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherData;
import pl.dolien.weatherService.entities.WeatherForecast;
import pl.dolien.weatherService.handlers.ForecastProcessingException;
import pl.dolien.weatherService.handlers.WeatherServiceException;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeatherForecastServiceTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherForecastService weatherForecastService;

    private List<Location> locations;

    private WeatherForecast forecast;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        locations = List.of(
                new Location("Jastarnia", "Poland"),
                new Location("Bridgetown", "Barbados"),
                new Location("Fortaleza", "Brazil"),
                new Location("Pissouri", "Cyprus"),
                new Location("Le Morne", "Mauritius")
        );

        forecast = new WeatherForecast();
        forecast.setLocation(locations.get(0));
        forecast.setData(List.of(
                new WeatherData(
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 15.0, 13.0, 20.0, 18.0
        )));
    }

    @Test
    void toWeatherForecasts_Success() {
        when(weatherService.getForecastForLocation(any(Location.class))).thenReturn(forecast);

        List<WeatherForecast> result = weatherForecastService.toWeatherForecasts();

        assertNotNull(result);
        assertEquals(5, result.size());
        assertEquals(locations.get(0), result.get(0).getLocation());
    }

    @Test
    void toWeatherForecasts_ServiceError() {
        when(weatherService.getForecastForLocation(any(Location.class))).thenThrow(new WeatherServiceException("Service failure"));

        Exception exception = assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.toWeatherForecasts();
        });

        assertEquals("Error processing forecasts for locations", exception.getMessage());
    }

    @Test
    void toWeatherForecasts_UnexpectedError() {
        when(weatherService.getForecastForLocation(any(Location.class))).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.toWeatherForecasts();
        });

        assertEquals("An unexpected error occurred while processing forecasts", exception.getMessage());
    }

    @Test
    void toLocationResponse_Success() {
        LocationResponse result = weatherForecastService.toLocationResponse(forecast, DATE_FORMAT.format(new Date()));

        assertNotNull(result);
        assertEquals("Jastarnia", result.getCityName());
    }

    @Test
    void toLocationResponse_ForecastEmpty() {
        Exception exception = assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.toLocationResponse(null, DATE_FORMAT.format(new Date()));
        });

        assertEquals("Invalid forecast data for conversion to LocationResponse", exception.getMessage());

    }

    @Test
    void toLocationResponse_UnexpectedError() {
        WeatherForecast forecast = mock(WeatherForecast.class);
        when(forecast.getData()).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.toLocationResponse(forecast, DATE_FORMAT.format(new Date()));
        });

        assertEquals("An unexpected error occurred while converting forecast to LocationResponse", exception.getMessage());
    }

    @Test
    void getBestForecastForDate_Success() {
        when(weatherService.getForecastForLocation(any(Location.class))).thenReturn(forecast);
        Date date = new Date();

        LocationResponse result = weatherForecastService.getBestForecastForDate(date);

        assertNotNull(result);
        assertEquals(forecast.getLocation().getCityName(), result.getCityName());
        assertEquals(forecast.getData().get(0).getAvg_temp(), result.getAvgTemp());
        assertEquals(forecast.getData().get(0).getWind_spd(), result.getWindSpd());
    }

    @Test
    void getBestForecastForDate_NoDataForDate() {
        when(weatherService.getForecastForLocation(any(Location.class))).thenReturn(forecast);
        Date date = new GregorianCalendar(2024, Calendar.AUGUST, 21).getTime();

        LocationResponse result = weatherForecastService.getBestForecastForDate(date);

        assertNull(result);
    }

    @Test
    void getBestForecastForDate_ServiceError() {
        when(weatherService.getForecastForLocation(any(Location.class))).thenThrow(new WeatherServiceException("Service failure"));

        Date date = new GregorianCalendar(2024, Calendar.AUGUST, 20).getTime();

        assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.getBestForecastForDate(date);
        });
    }

    @Test
    void isSuitableForWindsurfing_Success() {
        when(weatherService.getForecastForLocation(any(Location.class))).thenReturn(forecast);

        boolean result = weatherForecastService.isSuitableForWindsurfing(forecast.getData().get(0));

        assertTrue(result);
    }

    @Test
    void isSuitableForWindsurfing_NotSuitable() {
        WeatherData unsuitableData = new WeatherData();
        unsuitableData.setWind_spd(4.0); // Too low wind speed
        unsuitableData.setTemp(10.0);

        boolean result = weatherForecastService.isSuitableForWindsurfing(unsuitableData);

        assertFalse(result);
    }

    @Test
    void isSuitableForWindsurfing_UnexpectedError() {
        Exception exception = assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.isSuitableForWindsurfing(null);
        });

        assertEquals("Error evaluating windsurfing suitability", exception.getMessage());
    }

    @Test
    void calculateMaxScore_Success() {
        List<WeatherData> data = forecast.getData();
        double expectedMaxScore = data.stream()
                .mapToDouble(weatherForecastService::calculateScore)
                .max()
                .orElse(Double.NEGATIVE_INFINITY);

        double result = weatherForecastService.calculateMaxScore(forecast);

        assertEquals(expectedMaxScore, result);
    }

    @Test
    void calculateMaxScore_UnexpectedError() {
        Exception exception = assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.calculateMaxScore(null);
        });

        assertEquals("Error calculating max score for forecast", exception.getMessage());
    }

    @Test
    void calculateScore_Success() {
        double result = weatherForecastService.calculateScore(forecast.getData().get(0));

        assertEquals(forecast.getData().get(0).getWind_spd() * 3 + forecast.getData().get(0).getAvg_temp(), result);
    }

    @Test
    void calculateScore_UnexpectedError() {
        Exception exception = assertThrows(ForecastProcessingException.class, () -> {
            weatherForecastService.calculateScore(null);
        });

        assertEquals("Error calculating score for weather data", exception.getMessage());
    }
}
