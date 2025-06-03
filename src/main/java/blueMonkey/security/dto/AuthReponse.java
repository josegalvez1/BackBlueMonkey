package blueMonkey.security.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"username", "message", "jwt", "status" })
public record AuthReponse(String username,
                          String message,
                          String jwt,
                          boolean status,
                          List<String> roles) {
}
