package pl.dolien.weatherService.entities;

import lombok.*;

@Getter
@Setter
@Builder
public class WeatherData {
    private String datetime;
    private double temperature;
    private double avgTemperature;
    private double windSpeed;
}
