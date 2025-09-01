package co.com.crediya.r2dbc.adapters;

import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.loantype.gateways.LoanTypeGateway;
import co.com.crediya.r2dbc.entities.LoanTypeEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.repositories.LoanTypeReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType,
        LoanTypeEntity,
        Long,
        LoanTypeReactiveRepository
        > implements LoanTypeGateway {

    public LoanTypeReactiveRepositoryAdapter(
            LoanTypeReactiveRepository repository,
            ObjectMapper mapper
    ) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class));
    }
}

