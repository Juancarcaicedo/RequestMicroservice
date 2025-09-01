package co.com.crediya.r2dbc.repositories;

import co.com.crediya.r2dbc.entities.LoanStatusEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanStatusReactiveRepository extends ReactiveCrudRepository<LoanStatusEntity,Long>, ReactiveQueryByExampleExecutor<LoanStatusEntity> {
    Mono<LoanStatusEntity> findByCode(String code);
}
