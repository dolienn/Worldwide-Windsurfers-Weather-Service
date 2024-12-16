package pl.dolien.weatherService.mapper;

import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.entities.WeatherData;

import java.util.List;

public class WeatherMapper {

    private WeatherMapper() {}

    public static WeatherForecastDTO toWeatherDTO(Location location, List<WeatherData> data) {
        return WeatherForecastDTO.builder()
                .location(location)
                .forecastData(data)
                .build();
    }

    public static LocationResponse toLocationResponse(String cityName, WeatherData data) {
        return LocationResponse.builder()
                .cityName(cityName)
                .avgTemp(data.getAvgTemperature())
                .windSpd(data.getWindSpeed())
                .build();
    }
}
