package pl.dolien.weatherService.scorer;

import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.entities.WeatherData;

public class WeatherScorer {

    private WeatherScorer() {}

    public static double getHighestWeatherScore(WeatherForecastDTO weatherForecastDTO) {
        return weatherForecastDTO.getForecastData().stream()
                .mapToDouble(WeatherScorer::calculateWeatherScore)
                .max()
                .orElse(Double.NEGATIVE_INFINITY);
    }

    public static double calculateWeatherScore(WeatherData weatherData) {
        return (weatherData.getWindSpeed() * 3) + weatherData.getAvgTemperature();
    }
}
