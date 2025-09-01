package co.com.crediya.r2dbc.restconsumer;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserGateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
@Repository
public class UserRestConsumerAdapter implements UserGateway {
    private final WebClient webClient;

    public UserRestConsumerAdapter(WebClient.Builder builder,
                                   @Value("${services.auth.base-url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    @Override
    public Mono<User> findByDocument(String document) {
        return webClient.get()
                .uri("/api/v1/users/document/{document}", document)
                .retrieve()
                .bodyToMono(User.class)
                .onErrorResume(WebClientResponseException.NotFound.class, e -> {
                    return Mono.error(new RuntimeException("El cliente con documento " + document + " no existe"));
                })
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.error(new RuntimeException("Error al comunicarse con servicio de autenticación: " + e.getMessage()));
                })
                .onErrorResume(Exception.class, e -> {
                    return Mono.error(new RuntimeException("Error inesperado al validar cliente", e));
                });
    }

}
