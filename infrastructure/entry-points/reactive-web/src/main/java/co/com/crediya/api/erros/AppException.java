package co.com.crediya.api.erros;

public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String detail;

    public AppException(ErrorCode errorCode, String detail) {
        super(detail);
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getDetail() {
        return detail;
    }
}
