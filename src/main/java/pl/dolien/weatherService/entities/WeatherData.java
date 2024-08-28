package pl.dolien.weatherService.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    private String datetime;
    private double temp;
    private double min_temp;
    private double max_temp;
    private double wind_spd;

    public double getAvg_temp() {
        return (min_temp + max_temp) / 2;
    }
}
