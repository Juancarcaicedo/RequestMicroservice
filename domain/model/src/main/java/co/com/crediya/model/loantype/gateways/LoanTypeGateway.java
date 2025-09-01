package co.com.crediya.model.loantype.gateways;

import co.com.crediya.model.loantype.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeGateway {
    Mono<LoanType> findById(Long id);
}
