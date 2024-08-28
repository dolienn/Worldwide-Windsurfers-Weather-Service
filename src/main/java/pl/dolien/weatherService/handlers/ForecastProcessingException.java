package pl.dolien.weatherService.handlers;

public class ForecastProcessingException extends RuntimeException {
    public ForecastProcessingException(String message) {
        super(message);
    }

    public ForecastProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
