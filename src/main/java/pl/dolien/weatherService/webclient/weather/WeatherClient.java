package pl.dolien.weatherService.webclient.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.WeatherDTO;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;

    @Value("${weatherbit.apiKey}")
    private String apiKey;

    @Value("${weatherbit.url}")
    private String weatherbitUrl;

    public WeatherDTO getForecastForLocation(Location location) {
        String url = String.format("%s?city=%s&key=%s", weatherbitUrl, location.getCityName(), apiKey);
        WeatherDTO weatherDto = restTemplate.getForObject(url, WeatherDTO.class);

        assert weatherDto != null;
        weatherDto.setLocation(location);

        return weatherDto;
    }
}
