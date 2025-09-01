package co.com.crediya.model.loanstatus.gateways;

import co.com.crediya.model.loanstatus.LoanStatus;
import reactor.core.publisher.Mono;

public interface LoanStatusGateway {
    Mono<LoanStatus>findByCode(String code);
}
