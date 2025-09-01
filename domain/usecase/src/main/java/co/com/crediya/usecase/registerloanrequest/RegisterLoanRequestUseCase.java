package co.com.crediya.usecase.registerloanrequest;

import co.com.crediya.model.loanrequest.LoanRequest;
import co.com.crediya.model.loanrequest.gateways.LoanRequestGateway;
import co.com.crediya.model.loanstatus.gateways.LoanStatusGateway;
import co.com.crediya.model.loantype.gateways.LoanTypeGateway;
import co.com.crediya.model.user.gateways.UserGateway;
import exceptions.BusinessException;
import exceptions.BusinessRule;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RegisterLoanRequestUseCase {
    private final UserGateway userGateway;
    private final LoanTypeGateway loanTypeGateway;
    private final LoanStatusGateway loanStatusGateway;
    private final LoanRequestGateway loanRequestGateway;
    private static final String INITIAL_STATUS_CODE = "INITIAL_PENDING";
    public Mono<LoanRequest> execute(LoanRequest loanRequest) {
        return userGateway.findByDocument(loanRequest.getDocument())
                .switchIfEmpty(Mono.error(new BusinessException(BusinessRule.USER_NOT_FOUND)))
                .flatMap(user -> loanTypeGateway.findById(loanRequest.getLoanTypeId())
                        .switchIfEmpty(Mono.error(new BusinessException(BusinessRule.LOAN_TYPE_NOT_FOUND)))
                        .flatMap(loanType -> loanStatusGateway.findByCode(INITIAL_STATUS_CODE)
                                .switchIfEmpty(Mono.error(new BusinessException(BusinessRule.INITIAL_STATUS_NOT_FOUND)))
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



