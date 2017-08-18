package experiment.exceptions;

/**
 * Exception for errors affected by experiment settings.
 */
public class SettingsException extends RuntimeException {
    public SettingsException(String message) {
        super(message);
    }
}
