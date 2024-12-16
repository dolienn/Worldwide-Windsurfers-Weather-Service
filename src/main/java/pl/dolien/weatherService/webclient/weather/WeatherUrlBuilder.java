package pl.dolien.weatherService.webclient.weather;

public class WeatherUrlBuilder {

    public static String buildUrl(String cityName, String apiKey) {
        return String.format("?city=%s&key=%s", cityName, apiKey);
    }
}
