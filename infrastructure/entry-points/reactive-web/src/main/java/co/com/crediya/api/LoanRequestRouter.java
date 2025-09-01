package co.com.crediya.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LoanRequestRouter {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(LoanRequestHandler loanRequestHandler) {
        return route(POST("/api/v1/solicitud"), loanRequestHandler::registerLoanRequest);
    }
}
