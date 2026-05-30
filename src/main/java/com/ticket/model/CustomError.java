package com.ticket.model;

public class CustomError extends RuntimeException {

    private final int statusCode;
    private final String originClass;
    private final String details;

    public CustomError(int statusCode, String message, String originClass, String details) {
        super(message);
        this.statusCode = statusCode;
        this.originClass = originClass;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getOriginClass() {
        return originClass;
    }

    public String getDetails() {
        return details;
    }

    // Métodos estáticos para errores comunes
    public static CustomError badRequest(String message, String originClass, String details) {
        return new CustomError(400, message, originClass, details);
    }

    public static CustomError unauthorized(String message, String originClass) {
        return new CustomError(401, message, originClass, null);
    }

    public static CustomError forbidden(String message, String originClass) {
        return new CustomError(403, message, originClass, null);
    }

    public static CustomError notFound(String message, String originClass) {
        return new CustomError(404, message, originClass, null);
    }

    public static CustomError conflict(String message, String originClass, String details) {
        return new CustomError(409, message, originClass, details);
    }

    public static CustomError unprocessable(String message, String originClass, String details) {
        return new CustomError(422, message, originClass, details);
    }

    public static CustomError internalServer(String message, String originClass, String details) {
        return new CustomError(500, message, originClass, details);
    }
}


