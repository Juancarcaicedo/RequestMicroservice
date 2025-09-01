package co.com.crediya.api.erros;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;


@Configuration
@Order(-2) // se ejecuta antes del DefaultErrorWebExceptionHandler
public class GlobalExceptionHandlerConfig implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ErrorCode errorCode;
        String detail = ex.getMessage();

        if (ex instanceof AppException appEx) {
            errorCode = appEx.getErrorCode();
            detail = appEx.getDetail();
        } else {
            errorCode = ErrorCode.UNEXPECTED_ERROR;
        }

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(errorCode.getStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                detail
        );

        try {
            byte[] bytes = objectMapper
                    .writeValueAsString(errorResponse)
                    .getBytes(StandardCharsets.UTF_8);

            return response.writeWith(Mono.just(
                    response.bufferFactory().wrap(bytes)
            ));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    public record ErrorResponse(String code, String message, String detail) {}

    // excepción interna que se lanza con ErrorCode
    public static class AppException extends RuntimeException {
        private final ErrorCode errorCode;
        private final String detail;

        public AppException(ErrorCode errorCode, String detail) {
            super(detail);
            this.errorCode = errorCode;
            this.detail = detail;
        }

        public ErrorCode getErrorCode() { return errorCode; }
        public String getDetail() { return detail; }
    }
}

