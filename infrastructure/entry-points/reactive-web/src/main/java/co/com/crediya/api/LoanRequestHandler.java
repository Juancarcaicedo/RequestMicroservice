package co.com.crediya.api;

import co.com.crediya.api.dtos.request.LoanRequestDTO;
import co.com.crediya.api.mapper.LoanRequestMapper;
import co.com.crediya.usecase.registerloanrequest.RegisterLoanRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoanRequestHandler {
    private final RegisterLoanRequestUseCase registerLoanRequestUseCase;
    private final LoanRequestMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(LoanRequestHandler.class);
    public Mono<ServerResponse> registerLoanRequest(ServerRequest request) {
        return request.bodyToMono(LoanRequestDTO.class)
                .map(mapper::toDomain)
                .flatMap(registerLoanRequestUseCase::execute)
                .doOnNext(loanRequest -> log.info("Solicitud registrada: documento={}, tipoPrestamo={}, statusId={}",
                        loanRequest.getDocument(),
                        loanRequest.getLoanTypeId(),
                        loanRequest.getStatusId()))
                .map(mapper::toResponse)
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}
