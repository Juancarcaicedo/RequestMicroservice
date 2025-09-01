package co.com.crediya.r2dbc.adapters;

import co.com.crediya.model.loanrequest.LoanRequest;
import co.com.crediya.model.loanrequest.gateways.LoanRequestGateway;
import co.com.crediya.r2dbc.entities.LoanRequestEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.repositories.LoanRequestReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoanRequestRepositoryAdapter extends ReactiveAdapterOperations<
        LoanRequest/* change for domain model */,
        LoanRequestEntity/* change for adapter model */,
            Long,
        LoanRequestReactiveRepository
> implements LoanRequestGateway {
    public LoanRequestRepositoryAdapter(
            LoanRequestReactiveRepository repository,
            ObjectMapper mapper
    ) {
        super(repository, mapper, d -> mapper.map(d, LoanRequest.class));
    }

}
