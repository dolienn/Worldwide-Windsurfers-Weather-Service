package pl.dolien.weatherService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dolien.weatherService.entities.Location;
import pl.dolien.weatherService.entities.LocationResponse;
import pl.dolien.weatherService.entities.WeatherData;
import pl.dolien.weatherService.entities.WeatherForecast;
import pl.dolien.weatherService.handlers.ForecastProcessingException;
import pl.dolien.weatherService.handlers.WeatherServiceException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherForecastService {

    private static final double MIN_WIND_SPEED = 5.0;
    private static final double MAX_WIND_SPEED = 18.0;
    private static final double MIN_TEMP = 5.0;
    private static final double MAX_TEMP = 35.0;

    private final WeatherService weatherService;

    private final List<Location> locations = List.of(
            new Location("Jastarnia", "Poland", "PL"),
            new Location("Bridgetown", "Barbados", "BB"),
            new Location("Fortaleza", "Brazil", "BR"),
            new Location("Pissouri", "Cyprus", "CY"),
            new Location("Le Morne", "Mauritius", "MU")
    );

    public List<WeatherForecast> toWeatherForecasts() {
        try {
            return locations.stream()
                    .map(location -> {
                        WeatherForecast forecast = weatherService.getForecastForLocation(location);

                        List<WeatherData> suitableData = forecast.getData().stream()
                                .filter(this::isSuitableForWindsurfing)
                                .toList();

                        return new WeatherForecast(forecast.getLocation(), suitableData, forecast.getLat(), forecast.getLon());
                    })
                    .toList();
        } catch (WeatherServiceException e) {
            throw new ForecastProcessingException("Error processing forecasts for locations", e);
        } catch (Exception e) {
            throw new ForecastProcessingException("An unexpected error occurred while processing forecasts", e);
        }
    }

    public LocationResponse toLocationResponse(WeatherForecast forecast) {
        try {
            if (forecast == null || forecast.getData().isEmpty()) {
                throw new IllegalArgumentException();
            }

            return new LocationResponse(forecast.getLocation().getCityName(),
                    Math.round(forecast.getData().get(0).getAvg_temp() * 100.0) / 100.0,
                    forecast.getData().get(0).getWind_spd());
        } catch (IllegalArgumentException e) {
            throw new ForecastProcessingException("Invalid forecast data for conversion to LocationResponse", e);
        } catch (Exception e) {
            throw new ForecastProcessingException("An unexpected error occurred while converting forecast to LocationResponse", e);
        }
    }

    public LocationResponse getBestForecastForDate(Date date) {
        try {
            System.out.println(date);
            String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);


            List<WeatherForecast> forecastsForDate = toWeatherForecasts()
                    .stream()
                    .filter(forecast -> forecast.getData()
                            .stream()
                            .anyMatch(data -> data.getDatetime().equals(dateString)))
                    .toList();

            Optional<WeatherForecast> bestForecast = forecastsForDate
                    .stream()
                    .max((f1, f2) -> Double.compare(calculateMaxScore(f1), calculateMaxScore(f2)));

            return bestForecast.map(this::toLocationResponse).orElse(null);
        } catch (Exception e) {
            throw new ForecastProcessingException("An error occurred while finding the best forecast for date: " + date, e);
        }
    }

    protected boolean isSuitableForWindsurfing(WeatherData forecast) {
        try {
            return forecast.getWind_spd() >= MIN_WIND_SPEED && forecast.getWind_spd() <= MAX_WIND_SPEED
                    && forecast.getTemp() >= MIN_TEMP && forecast.getTemp() <= MAX_TEMP;
        } catch (Exception e) {
            throw new ForecastProcessingException("Error evaluating windsurfing suitability", e);
        }
    }

    protected double calculateMaxScore(WeatherForecast forecast) {
        try {
            return forecast.getData().stream()
                    .mapToDouble(this::calculateScore)
                    .max()
                    .orElse(Double.NEGATIVE_INFINITY);
        } catch (Exception e) {
            throw new ForecastProcessingException("Error calculating max score for forecast", e);
        }
    }

    protected double calculateScore(WeatherData forecast) {
        try {
            return Math.pow(forecast.getWind_spd(), 3) + forecast.getAvg_temp();
        } catch (Exception e) {
            throw new ForecastProcessingException("Error calculating score for weather data", e);
        }
    }
}