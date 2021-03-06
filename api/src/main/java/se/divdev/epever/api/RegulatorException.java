package se.divdev.epever.api;

public class RegulatorException extends Exception {
    public RegulatorException(String message) {
        super(message);
    }

    public RegulatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class ConnectException extends RegulatorException {
        public ConnectException(String message) {
            super(message);
        }

        public ConnectException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DisconnectException extends RegulatorException {
        public DisconnectException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class TimeoutException extends RegulatorException {
        public TimeoutException(Exception e) {
            super(e.getMessage(), e);
        }
    }

    public static class PortNotOpenedException extends RegulatorException {
        public PortNotOpenedException(Exception e) {
            super(e.getMessage(), e);
        }
    }
}
