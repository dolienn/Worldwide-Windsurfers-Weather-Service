package pl.dolien.weatherService.webclient.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.webclient.weather.dto.OpenWeatherWeatherDTO;

import static pl.dolien.weatherService.webclient.weather.WeatherClientMapper.toWeatherDTO;
import static pl.dolien.weatherService.webclient.weather.WeatherUrlBuilder.buildUrl;

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;

    @Value("${weatherbit.apiKey}")
    private String apiKey;

    @Value("${weatherbit.url}")
    private String weatherbitUrl;

    public WeatherForecastDTO getForecastForLocation(String cityName) {
        String url = buildUrl(cityName, apiKey);
        OpenWeatherWeatherDTO openWeatherWeatherDTO = callGetMethod(url, OpenWeatherWeatherDTO.class);
        System.out.println(openWeatherWeatherDTO);
        return toWeatherDTO(openWeatherWeatherDTO);
    }

    private <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(weatherbitUrl + url, responseType, objects);
    }
}
