package pl.dolien.weatherService.exceptions;

public class NoDataAvailableException extends RuntimeException {
    public NoDataAvailableException(String message) {
        super(message);
    }
}
