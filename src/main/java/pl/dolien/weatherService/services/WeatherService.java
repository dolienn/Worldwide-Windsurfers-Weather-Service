package pl.dolien.weatherService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherData;
import pl.dolien.weatherService.entities.WeatherForecastDTO;
import pl.dolien.weatherService.exceptions.NoDataAvailableException;
import pl.dolien.weatherService.filter.WeatherFilter;
import pl.dolien.weatherService.webclient.weather.WeatherClient;

import java.util.List;

import static pl.dolien.weatherService.mapper.WeatherMapper.toLocationResponse;
import static pl.dolien.weatherService.mapper.WeatherMapper.toWeatherDTO;
import static pl.dolien.weatherService.scorer.WeatherScorer.getHighestWeatherScore;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;
    private final WeatherFilter weatherFilter;
    private final LocationService locationService;

    public List<WeatherForecastDTO> getSuitableWeatherForecasts() {
        return locationService.getAllLocations().stream()
                .map(this::fetchAndFilterWeather)
                .toList();
    }

    public LocationResponse getBestForecastForDate(String date) {
        List<WeatherForecastDTO> forecastsForDate = filterForecastsByDate(date);

        return forecastsForDate.size() == 1
                ? createResponseFromForecast(forecastsForDate.get(0), date)
                : getHighestScoringForecast(forecastsForDate, date);
    }

    private WeatherForecastDTO fetchAndFilterWeather(Location location) {
        WeatherForecastDTO forecast = weatherClient.getForecastForLocation(location.getCityName());
        List<WeatherData> filteredData = weatherFilter.filterSuitableWeatherData(forecast.getForecastData());
        return toWeatherDTO(location, filteredData);
    }

    private List<WeatherForecastDTO> filterForecastsByDate(String date) {
        return getSuitableWeatherForecasts().stream()
                .filter(forecast -> weatherFilter.hasDataForDate(forecast, date))
                .toList();
    }

    private LocationResponse createResponseFromForecast(WeatherForecastDTO forecast, String targetDate) {
        WeatherData data = weatherFilter.getWeatherDataForDate(forecast, targetDate)
                .orElseThrow(() -> new NoDataAvailableException("No data available for the specified date"));
        return toLocationResponse(forecast.getLocation().getCityName(), data);
    }

    private LocationResponse getHighestScoringForecast(List<WeatherForecastDTO> forecasts, String date) {
        return forecasts.stream()
                .max(this::compareWeatherScores)
                .map(forecast -> createResponseFromForecast(forecast, date))
                .orElse(null);
    }

    private int compareWeatherScores(WeatherForecastDTO first, WeatherForecastDTO second) {
        double firstScore = getHighestWeatherScore(first);
        double secondScore = getHighestWeatherScore(second);
        return Double.compare(firstScore, secondScore);
    }
}