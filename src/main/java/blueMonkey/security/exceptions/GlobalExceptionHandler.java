package blueMonkey.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<CustomError> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        CustomError error = new CustomError(new Date(), 409, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotValidException.class)
    public ResponseEntity<CustomError> handleEmailNotValidException(EmailNotValidException ex) {
        CustomError error = new CustomError(new Date(), 422, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomError> handleUserNotFoundException(UserNotFoundException ex) {
        CustomError error = new CustomError(new Date(), 404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<CustomError> handleInvalidPasswordException(InvalidPasswordException ex) {
        CustomError error = new CustomError(new Date(), 422, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(InvalidFullnameException.class)
    public ResponseEntity<CustomError> handleInvalidFullnameException(InvalidFullnameException ex) {
        CustomError error = new CustomError(new Date(), 422, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<CustomError> handleUnprocessableEntity(UnprocessableEntityException ex) {
        CustomError error = new CustomError(new Date(), 422, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomError> handleEntityNotFound(EntityNotFoundException ex) {
        CustomError error = new CustomError(new Date(), 404, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomError> handleGlobalException(Exception ex) {
        CustomError error = new CustomError(new Date(), 500, "Error interno del servidor: " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<CustomError> handleAccessDenied(org.springframework.security.access.AccessDeniedException ex) {
        CustomError error = new CustomError(new Date(), 403, "No tienes permiso para acceder a este recurso");
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BookingConflictException.class)
    public ResponseEntity<CustomError> handleBookingConflictException(BookingConflictException ex) {
        CustomError error = new CustomError(new Date(), 409, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getReason());
        return new ResponseEntity<>(body, ex.getStatusCode());
    }


}
