package co.com.crediya.r2dbc.adapters;

import co.com.crediya.model.loanstatus.LoanStatus;
import co.com.crediya.model.loanstatus.gateways.LoanStatusGateway;
import co.com.crediya.r2dbc.entities.LoanStatusEntity;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import co.com.crediya.r2dbc.repositories.LoanStatusReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class LoanStatusRepositoryAdapter extends ReactiveAdapterOperations<
        LoanStatus,
        LoanStatusEntity,
        Long,
        LoanStatusReactiveRepository
        > implements LoanStatusGateway {

    private final LoanStatusReactiveRepository repository;
    private final ObjectMapper mapper;
    public LoanStatusRepositoryAdapter(
            LoanStatusReactiveRepository repository,
            ObjectMapper mapper
    ) {
        super(repository, mapper, d -> mapper.map(d, LoanStatus.class));
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<LoanStatus> findByCode(String code) {
        return repository.findByCode(code).map(entity -> mapper.map(entity, LoanStatus.class));
    }
}

