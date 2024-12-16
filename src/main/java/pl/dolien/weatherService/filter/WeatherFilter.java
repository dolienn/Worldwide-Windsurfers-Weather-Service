package pl.dolien.weatherService.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.entities.WeatherData;

import java.util.List;
import java.util.Optional;

@Component
public class WeatherFilter {

    @Value("${weather.min.wind-speed:5.0}")
    private double minWindSpeed;

    @Value("${weather.max.wind-speed:18.0}")
    private double maxWindSpeed;

    @Value("${weather.min.temperature:5.0}")
    private double minTemperature;

    @Value("${weather.max.temperature:35.0}")
    private double maxTemperature;

    public List<WeatherData> filterSuitableWeatherData(List<WeatherData> data) {
        return data.stream()
                .filter(this::isSuitableForWindsurfing)
                .toList();
    }

    public boolean isSuitableForWindsurfing(WeatherData weatherData) {
        return isWindSpeedValid(weatherData.getWindSpeed()) &&
                isTemperatureValid(weatherData.getTemperature());
    }

    public boolean hasDataForDate(WeatherForecastDTO weatherForecastDTO, String date) {
        return weatherForecastDTO.getForecastData().stream()
                .anyMatch(data -> data.getDatetime().equals(date));
    }

    public Optional<WeatherData> getWeatherDataForDate(WeatherForecastDTO forecast, String date) {
        return forecast.getForecastData().stream()
                .filter(data -> data.getDatetime().equals(date))
                .findFirst();
    }

    private boolean isWindSpeedValid(double windSpeed) {
        return windSpeed >= minWindSpeed && windSpeed <= maxWindSpeed;
    }

    private boolean isTemperatureValid(double temperature) {
        return temperature >= minTemperature && temperature <= maxTemperature;
    }
}
