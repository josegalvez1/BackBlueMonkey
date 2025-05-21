package blueMonkey.security.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String mensaje) {
        super(mensaje);
    }
}
