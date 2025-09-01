package co.com.crediya.r2dbc.repositories;

import co.com.crediya.r2dbc.entities.LoanRequestEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


public interface LoanRequestReactiveRepository extends ReactiveCrudRepository<LoanRequestEntity, Long>, ReactiveQueryByExampleExecutor<LoanRequestEntity> {

}
