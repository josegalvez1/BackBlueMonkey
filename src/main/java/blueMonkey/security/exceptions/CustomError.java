package blueMonkey.security.exceptions;

import java.util.Date;

public class CustomError {
    private Date timestamp;
    private int httpCode;
    private String mensaje;

    public CustomError(Date timestamp, int httpCode, String mensaje) {
        this.timestamp = timestamp;
        this.httpCode = httpCode;
        this.mensaje = mensaje;
    }

    // Getters y setters
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
