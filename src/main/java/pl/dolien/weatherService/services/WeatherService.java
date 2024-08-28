package pl.dolien.weatherService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.WeatherForecast;
import pl.dolien.weatherService.handlers.WeatherServiceException;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final RestTemplate restTemplate;
    @Value("${weatherbit.apiKey}")
    private String apiKey;
    @Value("${weatherbit.url}")
    private String weatherbitUrl;

    public WeatherForecast getForecastForLocation(Location location) {
        try {
            String url = String.format("%s?city=%s&key=%s", weatherbitUrl, location.getCityName(), apiKey);
            ResponseEntity<WeatherForecast> response = restTemplate.getForEntity(url, WeatherForecast.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                WeatherForecast forecast = response.getBody();
                if (forecast != null) {
                    forecast.setLocation(location);
                    return forecast;
                } else {
                    throw new WeatherServiceException("No weather forecast data found for location: " + location.getCityName());
                }
            } else {
                throw new WeatherServiceException("Failed to retrieve weather forecast. Status code: " + response.getStatusCode());
            }
        } catch (RestClientException e) {
            throw new WeatherServiceException("Error while calling weather service", e);
        }
    }
}
