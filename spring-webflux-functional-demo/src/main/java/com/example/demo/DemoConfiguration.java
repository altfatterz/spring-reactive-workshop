package com.example.demo;

import com.example.demo.customer.CustomerHandler;
import com.example.demo.customer.CustomerRepository;
import com.example.demo.security.RandomSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.example.demo.security.SecurityManager;
import com.example.demo.security.RandomSecurityManager;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_HTML;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;


@Configuration
public class DemoConfiguration {

    private SecurityManager securityManager = new RandomSecurityManager();

    @Bean
    public CustomerHandler petHandler(CustomerRepository repository) {
        return new CustomerHandler(repository);
    }

    @Bean
    public RouterFunction<ServerResponse> customerRoutes(CustomerHandler customerHandler) {
        RouterFunction<ServerResponse> html = route()
                .GET("/customers/{id}", accept(TEXT_HTML), customerHandler::renderCustomer)
                .GET("/customers", accept(TEXT_HTML), customerHandler::renderCustomers)
                .build();

        RouterFunction<ServerResponse> json = route()
                .GET("/customers/{id}", accept(APPLICATION_JSON), customerHandler::getCustomer)
                .GET("/customers", accept(APPLICATION_JSON), customerHandler::getCustomers)
                .build();

        return html.and(json).filter(this::security);

    }

    public Mono<ServerResponse> security(ServerRequest request, HandlerFunction<ServerResponse> next) {
        if (this.securityManager.hasAccess(request)) {
            return next.handle(request);
        }
        else {
            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
