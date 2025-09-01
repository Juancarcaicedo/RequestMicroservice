package co.com.crediya.api.erros;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    VALIDATION_ERROR("VALIDATION_ERROR", "La solicitud es inválida", HttpStatus.BAD_REQUEST, Level.WARN),
    BUSINESS_RULE_VIOLATION("BUSINESS_RULE_VIOLATION", "Regla de negocio violada", HttpStatus.CONFLICT, Level.WARN),
    NOT_FOUND("NOT_FOUND", "Recurso no encontrado", HttpStatus.NOT_FOUND, Level.INFO),
    DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION", "Violación de integridad de datos", HttpStatus.CONFLICT, Level.WARN),
    DEPENDENCY_CONFLICT("DEPENDENCY_CONFLICT", "Conflicto al comunicarse con un servicio externo", HttpStatus.CONFLICT, Level.WARN),
    DEPENDENCY_UNAVAILABLE("DEPENDENCY_UNAVAILABLE", "Servicio externo no disponible", HttpStatus.SERVICE_UNAVAILABLE, Level.ERROR),
    DEPENDENCY_ERROR("DEPENDENCY_ERROR", "Error inesperado en servicio externo", HttpStatus.BAD_GATEWAY, Level.ERROR),
    TIMEOUT("TIMEOUT", "Tiempo de espera agotado al comunicarse con un servicio externo", HttpStatus.GATEWAY_TIMEOUT, Level.WARN),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR, Level.ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;
    private final Level level;

    ErrorCode(String code, String message, HttpStatus status, Level level) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.level = level;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getStatus() { return status; }
    public Level getLevel() { return level; }

    public enum Level { INFO, WARN, ERROR }
}
