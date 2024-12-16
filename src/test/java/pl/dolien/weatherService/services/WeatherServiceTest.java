package pl.dolien.weatherService.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherData;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.exceptions.NoDataAvailableException;
import pl.dolien.weatherService.filter.WeatherFilter;
import pl.dolien.weatherService.webclient.weather.WeatherClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    private static final String NO_DATA_AVAILABLE_MESSAGE = "No data available for the specified date";

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private WeatherFilter weatherFilter;

    @Mock
    private LocationService locationService;

    private Location testLocation1;
    private Location testLocation2;
    private WeatherData testWeatherData1;
    private WeatherData testWeatherData2;
    private WeatherForecastDTO testForecastDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initializeTestData();
    }

    @Test
    void shouldReturnSuitableWeatherForecasts() {
        mockForSuitableWeatherForecasts();

        List<WeatherForecastDTO> response = weatherService.getSuitableWeatherForecasts();

        assertSuitableWeatherForecasts(response);
        verifyForSuitableWeatherForecasts();
    }

    @Test
    void shouldReturnBestForecastForDate() {
        mockForBestWeatherForecastForDate();

        LocationResponse response = weatherService.getBestForecastForDate("2022-01-01");

        assertLocationResponse(response);
        verifyForBestWeatherForecastForDate();
    }

    @Test
    void shouldReturnBestForecastForDateWhenOneForecastIsSuitable() {
        mockForBestWeatherForecastForDateWithOneLocation();

        LocationResponse response = weatherService.getBestForecastForDate("2022-01-01");

        assertLocationResponse(response);
        verifyForBestWeatherForecastForDateWithOneLocation();
    }

    @Test
    void shouldReturnNullForBestForecastForDate() {
        mockForSuitableWeatherForecastsWithNoData();

        LocationResponse response = weatherService.getBestForecastForDate("2022-01-01");

        assertNull(response);
        verifyForSuitableWeatherForecasts();
    }

    @Test
    void shouldThrowNoDataAvailableException() {
        mockForSuitableWeatherForecastsWithNoDataException();

        NoDataAvailableException exception = assertThrows(NoDataAvailableException.class,
                () -> weatherService.getBestForecastForDate("2022-01-01"));

        assertEquals(NO_DATA_AVAILABLE_MESSAGE, exception.getMessage());
    }

    private void initializeTestData() {
        testLocation1 = Location.builder()
                .cityName("Jastarnia")
                .countryName("Poland")
                .build();

        testLocation2 = Location.builder()
                .cityName("Bridgetown")
                .countryName("Barbados")
                .build();

        testWeatherData1 = WeatherData.builder()
                .datetime("2022-01-01")
                .temperature(10.0)
                .avgTemperature(10.0)
                .windSpeed(10.0)
                .build();

        testWeatherData2 = WeatherData.builder()
                .datetime("2022-01-02")
                .temperature(20.0)
                .avgTemperature(20.0)
                .windSpeed(20.0)
                .build();

        testForecastDTO = WeatherForecastDTO.builder()
                .location(testLocation1)
                .forecastData(List.of(testWeatherData1, testWeatherData2))
                .build();
    }

    private void mockForSuitableWeatherForecasts() {
        when(locationService.getAllLocations()).thenReturn(List.of(testLocation1, testLocation2));
        when(weatherClient.getForecastForLocation(anyString())).thenReturn(testForecastDTO);
        when(weatherFilter.filterSuitableWeatherData(anyList()))
                .thenReturn(List.of(testWeatherData1, testWeatherData2));
    }

    private void mockForSuitableWeatherForecastsWithOneLocation() {
        when(locationService.getAllLocations()).thenReturn(List.of(testLocation1));
        when(weatherClient.getForecastForLocation(anyString())).thenReturn(testForecastDTO);
        when(weatherFilter.filterSuitableWeatherData(anyList()))
                .thenReturn(List.of(testWeatherData1));
    }

    private void mockForSuitableWeatherForecastsWithNoData() {
        when(locationService.getAllLocations()).thenReturn(List.of(testLocation1, testLocation2));
        when(weatherClient.getForecastForLocation(anyString())).thenReturn(testForecastDTO);
        when(weatherFilter.filterSuitableWeatherData(anyList()))
                .thenReturn(List.of());
    }

    private void mockForBestWeatherForecastForDate() {
        mockForSuitableWeatherForecasts();
        when(weatherFilter.hasDataForDate(any(WeatherForecastDTO.class), anyString())).thenReturn(true);
         // testLocation1 has higher score
        when(weatherFilter.getWeatherDataForDate(any(WeatherForecastDTO.class), anyString()))
                .thenReturn(Optional.of(testWeatherData1));
    }

    private void mockForBestWeatherForecastForDateWithOneLocation() {
        mockForSuitableWeatherForecastsWithOneLocation();
        when(weatherFilter.hasDataForDate(any(WeatherForecastDTO.class), anyString())).thenReturn(true);
        when(weatherFilter.getWeatherDataForDate(any(WeatherForecastDTO.class), anyString()))
                .thenReturn(Optional.of(testWeatherData1));
    }

    private void mockForSuitableWeatherForecastsWithNoDataException() {
        mockForSuitableWeatherForecasts();
        when(weatherFilter.hasDataForDate(any(WeatherForecastDTO.class), anyString())).thenReturn(true);
        when(weatherFilter.getWeatherDataForDate(any(WeatherForecastDTO.class), anyString()))
                .thenReturn(Optional.empty());
    }

    private void assertSuitableWeatherForecasts(List<WeatherForecastDTO> response) {
        assertEquals(2, response.size());
        assertEquals(testLocation1, response.get(0).getLocation());
        assertEquals(testLocation2, response.get(1).getLocation());
    }

    private void assertLocationResponse(LocationResponse response) {
        assertEquals(testLocation1.getCityName(), response.getCityName());
        assertEquals(testWeatherData1.getWindSpeed(), response.getWindSpd());
        assertEquals(testWeatherData1.getAvgTemperature(), response.getAvgTemp());
    }

    private void verifyForSuitableWeatherForecasts() {
        verify(locationService, times(1)).getAllLocations();
        verify(weatherClient, times(1)).getForecastForLocation(testLocation1.getCityName());
        verify(weatherFilter, times(2))
                .filterSuitableWeatherData(testForecastDTO.getForecastData());
    }

    private void verifyForSuitableWeatherForecastsWithOneLocation() {
        verify(locationService, times(1)).getAllLocations();
        verify(weatherClient, times(1)).getForecastForLocation(testLocation1.getCityName());
        verify(weatherFilter, times(1))
                .filterSuitableWeatherData(testForecastDTO.getForecastData());
    }

    private void verifyForBestWeatherForecastForDate() {
        verifyForSuitableWeatherForecasts();
        verify(weatherFilter, times(2))
                .hasDataForDate(any(WeatherForecastDTO.class), anyString());
    }

    private void verifyForBestWeatherForecastForDateWithOneLocation() {
        verifyForSuitableWeatherForecastsWithOneLocation();
        verify(weatherFilter, times(1))
                .hasDataForDate(any(WeatherForecastDTO.class), anyString());
    }
}
