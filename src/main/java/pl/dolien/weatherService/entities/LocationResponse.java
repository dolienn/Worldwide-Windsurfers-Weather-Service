package pl.dolien.weatherService.entities;

import lombok.*;

@Getter
@Setter
@Builder
public class LocationResponse {
    private String cityName;
    private double avgTemp;
    private double windSpd;
}
