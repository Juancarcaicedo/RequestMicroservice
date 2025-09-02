package co.com.crediya.api.erros;


import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.BusinessException;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
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
        String detail;

        if (ex instanceof AppException appEx) {
            errorCode = appEx.getErrorCode();
            detail = appEx.getDetail();
        } else if (ex instanceof BusinessException businessEx) {
            errorCode = ErrorCode.BUSINESS_RULE_VIOLATION;
            detail = businessEx.getRule().getMessage();

        }

        else {
            errorCode = ErrorCode.UNEXPECTED_ERROR;
            detail = ex.getMessage();
        }

        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage(),
                detail,
                errorCode.getLevel().name()
        );

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(errorCode.getStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            byte[] bytes = objectMapper
                    .writeValueAsString(errorResponse)
                    .getBytes(StandardCharsets.UTF_8);

            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
    private Throwable unwrap(Throwable ex) {
        while (ex.getCause() != null && !(ex instanceof AppException || ex instanceof BusinessException)) {
            ex = ex.getCause();
        }
        return ex;
    }
    public record ErrorResponse(
            String code,
            String message,
            String detail,
            String level
    ) {}
}


