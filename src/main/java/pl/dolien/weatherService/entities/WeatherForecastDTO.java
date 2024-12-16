package pl.dolien.weatherService.entities;

import lombok.*;

import java.util.List;

@Getter
@Builder
public class WeatherForecastDTO {
    private Location location;
    private List<WeatherData> forecastData;
}