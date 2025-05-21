package blueMonkey.security.exceptions;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException(String mensaje) {
        super(mensaje);
    }
}
