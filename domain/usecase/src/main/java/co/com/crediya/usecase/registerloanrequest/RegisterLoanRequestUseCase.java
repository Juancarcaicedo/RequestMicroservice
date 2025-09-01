package co.com.crediya.usecase.registerloanrequest;

import co.com.crediya.model.loanrequest.LoanRequest;
import co.com.crediya.model.loanrequest.gateways.LoanRequestGateway;
import co.com.crediya.model.loanstatus.gateways.LoanStatusGateway;
import co.com.crediya.model.loantype.gateways.LoanTypeGateway;
import co.com.crediya.model.user.gateways.UserGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class RegisterLoanRequestUseCase {
    private final UserGateway userGateway;
    private final LoanTypeGateway loanTypeGateway;
    private final LoanStatusGateway loanStatusGateway;
    private final LoanRequestGateway loanRequestGateway;

    public Mono<LoanRequest> execute(LoanRequest loanRequest) {
        return userGateway.findByDocument(loanRequest.getDocument())
                .switchIfEmpty(Mono.error(new RuntimeException("Cliente no existe")))
                .flatMap(user -> loanTypeGateway.findById(loanRequest.getLoanTypeId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Tipo de préstamo inválido")))
                        .flatMap(loanType -> loanStatusGateway.findByName("Pendiente de revisión")
                                .switchIfEmpty(Mono.error(new RuntimeException("Estado inicial no disponible")))
                                .flatMap(status -> {
                                    LoanRequest enrichedRequest = loanRequest.toBuilder()
                                            .statusId(status.getIdStatus())
                                            .build();

                                    return loanRequestGateway.save(enrichedRequest);
                                })
                        )
                );
    }


}

