package blueMonkey.security.dto;

public record AuthResetPasswordRequest(String token, String newPassword) {}
