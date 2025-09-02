package co.com.crediya.usecase.registerloanrequest;

import co.com.crediya.model.loanrequest.LoanRequest;
import co.com.crediya.model.loanrequest.gateways.LoanRequestGateway;
import co.com.crediya.model.loanstatus.LoanStatus;
import co.com.crediya.model.loanstatus.gateways.LoanStatusGateway;
import co.com.crediya.model.loantype.LoanType;
import co.com.crediya.model.loantype.gateways.LoanTypeGateway;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserGateway;
import exceptions.BusinessException;
import exceptions.BusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class RegisterLoanRequestUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private LoanTypeGateway loanTypeGateway;

    @Mock
    private LoanStatusGateway loanStatusGateway;

    @Mock
    private LoanRequestGateway loanRequestGateway;

    private RegisterLoanRequestUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new RegisterLoanRequestUseCase(userGateway, loanTypeGateway, loanStatusGateway, loanRequestGateway);
    }

    @Test
    void shouldThrowUserNotFoundException() {
        // Construimos una solicitud con datos válidos, excepto el documento
        LoanRequest request = LoanRequest.builder()
                .document("999999") // Documento que no existe
                .loanTypeId(1L)
                .amount(BigDecimal.valueOf(5000))
                .termInMonths(12)
                .email("pepe@ejemplo.com")
                .build();

        // Simulamos que el usuario no existe
        Mockito.when(userGateway.findByDocument("999999"))
                .thenReturn(Mono.empty());
        StepVerifier.create(useCase.execute(request))
                .expectErrorMatches(error ->
                        error instanceof BusinessException &&
                                ((BusinessException) error).getRule() == BusinessRule.USER_NOT_FOUND
                )
                .verify();
    }
    @Test
    void shouldThrowLoanTypeNotFoundException() {
        LoanRequest request = LoanRequest.builder()
                .document("123456")
                .loanTypeId(99L) // ID inválido
                .amount(BigDecimal.valueOf(10000))
                .termInMonths(24)
                .email("cliente@ejemplo.com")
                .build();

        Mockito.when(userGateway.findByDocument("123456"))
                .thenReturn(Mono.just(new User()));
        Mockito.when(loanTypeGateway.findById(99L))
                .thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(request))
                .expectErrorMatches(error ->
                        error instanceof BusinessException &&
                                ((BusinessException) error).getRule() == BusinessRule.LOAN_TYPE_NOT_FOUND
                )
                .verify();
    }
    @Test
    void shouldThrowInitialStatusNotFoundException() {
        LoanRequest request = LoanRequest.builder()
                .document("123456")
                .loanTypeId(1L)
                .amount(BigDecimal.valueOf(15000))
                .termInMonths(36)
                .email("cliente@ejemplo.com")
                .build();

        Mockito.when(userGateway.findByDocument("123456"))
                .thenReturn(Mono.just(new User()));
        Mockito.when(loanTypeGateway.findById(1L))
                .thenReturn(Mono.just(new LoanType()));
        Mockito.when(loanStatusGateway.findByCode("INITIAL_PENDING"))
                .thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(request))
                .expectErrorMatches(error ->
                        error instanceof BusinessException &&
                                ((BusinessException) error).getRule() == BusinessRule.INITIAL_STATUS_NOT_FOUND
                )
                .verify();
    }

    @Test
    void shouldSaveLoanRequestSuccessfully() {
        String document = "123456";
        Long loanTypeId = 1L;
        BigDecimal amount = BigDecimal.valueOf(20000);
        Integer termInMonths = 48;
        String email = "cliente@ejemplo.com";
        Long expectedStatusId = 1L;

        LoanRequest request = LoanRequest.builder()
                .document(document)
                .loanTypeId(loanTypeId)
                .amount(amount)
                .termInMonths(termInMonths)
                .email(email)
                .build();

        LoanStatus status = new LoanStatus();
        status.setIdStatus(expectedStatusId);

        Mockito.when(userGateway.findByDocument(document))
                .thenReturn(Mono.just(new User()));

        Mockito.when(loanTypeGateway.findById(loanTypeId))
                .thenReturn(Mono.just(new LoanType()));

        Mockito.when(loanStatusGateway.findByCode("INITIAL_PENDING"))
                .thenReturn(Mono.just(status));

        Mockito.when(loanRequestGateway.save(Mockito.any()))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0))); // ✅ devuelve lo que recibe

        StepVerifier.create(useCase.execute(request))
                .expectNextMatches(result ->
                        result.getStatusId().equals(expectedStatusId) &&
                                result.getAmount().equals(amount) &&
                                result.getEmail().equals(email)
                )
                .verifyComplete();
    }


}
