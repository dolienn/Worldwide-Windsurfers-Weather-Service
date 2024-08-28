package pl.dolien.weatherService.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.WeatherForecast;
import pl.dolien.weatherService.handlers.WeatherServiceException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getForecastForLocation_Success() {
        Location location = new Location("San Francisco", "USA", "US");
        WeatherForecast forecast = new WeatherForecast();
        forecast.setLocation(location);

        ResponseEntity<WeatherForecast> responseEntity = new ResponseEntity<>(forecast, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(WeatherForecast.class))).thenReturn(responseEntity);

        WeatherForecast result = weatherService.getForecastForLocation(location);

        assertNotNull(result);
        assertEquals(location, result.getLocation());
    }

    @Test
    void getForecastForLocation_NoData() {
        Location location = new Location("San Francisco", "USA", "US");
        ResponseEntity<WeatherForecast> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(WeatherForecast.class))).thenReturn(responseEntity);

        Exception exception = assertThrows(WeatherServiceException.class, () -> {
            weatherService.getForecastForLocation(location);
        });

        assertEquals("No weather forecast data found for location: San Francisco", exception.getMessage());
    }

    @Test
    void getForecastForLocation_FailedRequest() {
        Location location = new Location("San Francisco", "USA", "US");
        when(restTemplate.getForEntity(anyString(), eq(WeatherForecast.class)))
                .thenThrow(new RestClientException("Request failed"));

        Exception exception = assertThrows(WeatherServiceException.class, () -> {
            weatherService.getForecastForLocation(location);
        });

        assertEquals("Error while calling weather service", exception.getMessage());
    }

    @Test
    void getForecastForLocation_BadResponse() {
        Location location = new Location("San Francisco", "USA", "US");
        ResponseEntity<WeatherForecast> responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        when(restTemplate.getForEntity(anyString(), eq(WeatherForecast.class))).thenReturn(responseEntity);

        Exception exception = assertThrows(WeatherServiceException.class, () -> {
            weatherService.getForecastForLocation(location);
        });

        assertEquals("Failed to retrieve weather forecast. Status code: 400 BAD_REQUEST", exception.getMessage());
    }
}
