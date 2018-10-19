package ch.open;

import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientExample {


    public void hello() {

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector()).build();

        WebClient.create();


        String id = "1";

        WebClient client = WebClient.create("http://example.com");

        Mono<Customer> customer = client.get().
                uri("/customers/{id}", id).accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(Customer.class);

        Flux<Customer> customers = client.get().
                uri("/customers", id).accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve().bodyToFlux(Customer.class);


    }
}
