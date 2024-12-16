package pl.dolien.weatherService.webclient.weather;

import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.entities.WeatherData;
import pl.dolien.weatherService.webclient.weather.dto.OpenWeatherDataDTO;
import pl.dolien.weatherService.webclient.weather.dto.OpenWeatherWeatherDTO;

public class WeatherClientMapper {

    private WeatherClientMapper() {}

    public static WeatherForecastDTO toWeatherDTO(OpenWeatherWeatherDTO openWeatherWeatherDTO) {
        return WeatherForecastDTO.builder()
                .location(toLocation(openWeatherWeatherDTO))
                .forecastData(openWeatherWeatherDTO.getData().stream()
                        .map(WeatherClientMapper::toWeatherData).toList())
                .build();
    }

    private static Location toLocation(OpenWeatherWeatherDTO openWeatherWeatherDTO) {
        return Location.builder()
                .cityName(openWeatherWeatherDTO.getCity_name())
                .countryName(openWeatherWeatherDTO.getCountry_code())
                .build();
    }

    private static WeatherData toWeatherData(OpenWeatherDataDTO openWeatherDataDTO) {
        return WeatherData.builder()
                .datetime(openWeatherDataDTO.datetime())
                .temperature(openWeatherDataDTO.temp())
                .avgTemperature(openWeatherDataDTO.getAvg_temp())
                .windSpeed(openWeatherDataDTO.wind_spd())
                .build();
    }
}
