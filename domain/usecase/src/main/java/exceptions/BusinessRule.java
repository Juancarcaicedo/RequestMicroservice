package exceptions;

public enum BusinessRule {
    LOAN_TYPE_NOT_FOUND("El tipo de préstamo no es válido"),
    INITIAL_STATUS_NOT_FOUND("No se pudo asignar el estado inicial"),
    USER_NOT_FOUND("Usuario No Encontrado");
    private final String message;

    BusinessRule(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
