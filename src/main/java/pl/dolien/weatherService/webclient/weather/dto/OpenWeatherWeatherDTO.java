package pl.dolien.weatherService.webclient.weather.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OpenWeatherWeatherDTO {
    private String city_name;
    private String country_code;
    private List<OpenWeatherDataDTO> data;
}
