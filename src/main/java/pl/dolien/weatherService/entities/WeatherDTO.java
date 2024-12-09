package pl.dolien.weatherService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDTO {
    private Location location;
    private List<WeatherData> data;
    private double lat;
    private double lon;
}