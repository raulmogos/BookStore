package api;

public class TcpConnectionError extends RuntimeException {

    public TcpConnectionError(String message, Throwable cause) {
        super(message, cause);
    }

    public TcpConnectionError(Throwable cause) {
        super(cause);
    }
}
