package pl.dolien.weatherService.webclient.weather.dto;

public record OpenWeatherDataDTO(
        String datetime,
        double temp,
        double min_temp,
        double max_temp,
        double wind_spd
) {
    public double getAvg_temp() {
        return (min_temp + max_temp) / 2;
    }
}
