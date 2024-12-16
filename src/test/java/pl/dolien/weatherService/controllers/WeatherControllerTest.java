package pl.dolien.weatherService.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.services.WeatherService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;

    private MockMvc mockMvc;
    private WeatherForecastDTO testForecastDTO;
    private LocationResponse testLocationResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
        initializeTestData();
    }

    @Test
    void shouldReturnSuitableWeatherForecasts() throws Exception {
        when(weatherService.getSuitableWeatherForecasts()).thenReturn(List.of(testForecastDTO));

        performGetSuitableWeatherForecastsRequest()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(testForecastDTO))));

        verify(weatherService, times(1)).getSuitableWeatherForecasts();
    }

    @Test
    void shouldReturnBestForecast() throws Exception {
        when(weatherService.getBestForecastForDate(anyString())).thenReturn(testLocationResponse);

        performGetBestForecastRequest()
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testLocationResponse)));

        verify(weatherService, times(1)).getBestForecastForDate(anyString());
    }

    private void initializeTestData() {
        testForecastDTO = WeatherForecastDTO.builder().build();

        testLocationResponse = LocationResponse.builder()
                .cityName("Jastarnia")
                .avgTemp(10.0)
                .windSpd(10.0)
                .build();
    }

    private ResultActions performGetSuitableWeatherForecastsRequest() throws Exception {
        return mockMvc.perform(get("/api/windsurfing/locations")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testForecastDTO)));
    }

    private ResultActions performGetBestForecastRequest() throws Exception {
        String testDate = "01-01-2022";

        return mockMvc.perform(get("/api/windsurfing/best-location")
                .contentType(APPLICATION_JSON)
                .param("date", testDate));
    }
}