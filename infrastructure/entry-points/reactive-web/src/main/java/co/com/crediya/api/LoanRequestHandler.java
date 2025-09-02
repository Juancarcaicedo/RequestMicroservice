package co.com.crediya.api;

import co.com.crediya.api.dtos.request.LoanRequestDTO;
import co.com.crediya.api.erros.AppException;
import co.com.crediya.api.erros.ErrorCode;
import co.com.crediya.api.mapper.LoanRequestMapper;
import co.com.crediya.usecase.registerloanrequest.RegisterLoanRequestUseCase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class LoanRequestHandler {
    private final RegisterLoanRequestUseCase registerLoanRequestUseCase;
    private final LoanRequestMapper mapper;
    private final Validator validator;
    private static final Logger log = LoggerFactory.getLogger(LoanRequestHandler.class);
    public Mono<ServerResponse> registerLoanRequest(ServerRequest request) {
        log.info("Iniciando registro de solicitud de préstamo");

        return request.bodyToMono(LoanRequestDTO.class)
                .doOnNext(dto -> log.debug("DTO recibido: {}", dto))
                .flatMap(dto -> {
                    Set<ConstraintViolation<LoanRequestDTO>> violations = validator.validate(dto);
                    if (!violations.isEmpty()) {
                        String errorMessage = violations.iterator().next().getMessage();
                        log.warn("Fallo al validar solicitud: {}", errorMessage);

                        // Lanzamos excepción semántica
                        throw new AppException(ErrorCode.VALIDATION_ERROR, errorMessage);
                    }

                    return registerLoanRequestUseCase.execute(mapper.toDomain(dto))
                            .doOnNext(loan -> log.info("Solicitud registrada: documento={}, tipoPrestamo={}, statusId={}",
                                    loan.getDocument(), loan.getLoanTypeId(), loan.getStatusId()))
                            .map(mapper::toResponse)
                            .flatMap(response -> {
                                log.debug("Respuesta enviada: {}", response);
                                return ServerResponse.status(HttpStatus.CREATED).bodyValue(response);
                            });
                });
    }
}

