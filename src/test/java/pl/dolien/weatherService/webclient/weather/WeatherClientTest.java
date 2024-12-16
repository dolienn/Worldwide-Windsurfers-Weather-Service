package pl.dolien.weatherService.webclient.weather;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.webclient.weather.dto.OpenWeatherDataDTO;
import pl.dolien.weatherService.webclient.weather.dto.OpenWeatherWeatherDTO;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class WeatherClientTest {

    @InjectMocks
    private WeatherClient weatherClient;

    @Mock
    private RestTemplate restTemplate;

    OpenWeatherWeatherDTO openWeatherWeatherDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(weatherClient, "apiKey", "mock-api-key");
        ReflectionTestUtils.setField(weatherClient, "weatherbitUrl", "http://mock-weatherbit-url/");
        initializeTestData();
    }

    @Test
    void shouldReturnForecastForLocation() {
        ArgumentCaptor<Object[]> captor = ArgumentCaptor.forClass(Object[].class);
        when(restTemplate.getForObject(anyString(), any(), captor.capture()))
                .thenReturn(openWeatherWeatherDTO);

        WeatherForecastDTO weatherForecastDTO = weatherClient.getForecastForLocation("Warsaw");

        assertForecast(weatherForecastDTO);
        verify(restTemplate, times(1)).getForObject(anyString(), any(), captor.capture());
    }

    private void initializeTestData() {
        openWeatherWeatherDTO = OpenWeatherWeatherDTO.builder()
                .city_name("Warsaw")
                .country_code("PL")
                .data(Collections.singletonList(
                        new OpenWeatherDataDTO("2022-01-01", 20.0, 15.0, 25.0, 10.0)
                ))
                .build();
    }

    private void assertForecast(WeatherForecastDTO weatherForecastDTO) {
        assertEquals(openWeatherWeatherDTO.getCity_name(), weatherForecastDTO.getLocation().getCityName());
        assertEquals(openWeatherWeatherDTO.getCountry_code(), weatherForecastDTO.getLocation().getCountryName());
        assertEquals(openWeatherWeatherDTO.getData().size(), weatherForecastDTO.getForecastData().size());
    }
}