package co.com.crediya.model.loanrequest.gateways;

import co.com.crediya.model.loanrequest.LoanRequest;
import reactor.core.publisher.Mono;

public interface LoanRequestGateway {
    Mono<LoanRequest> save(LoanRequest loanRequest);
}
