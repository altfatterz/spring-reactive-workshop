package ch.open.temperaturedashboard;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

@Controller
public class Measurement2Controller {

    private final WebClient webClient;

    public Measurement2Controller(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/reactive-template")
    public String measurements(final Model model) {
        Flux<Measurement> measurements = webClient
                .get()
                .uri("/measurements")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(Measurement.class);

        // Creates a new lazy context variable, wrapping a reactive asynchronous data stream
        // and specifying a buffer size.
        // Sets Thymeleaf in data-driven mode in order to produce (render) Server-Sent Events
        // as the Flux produces values.
        // This object works as a wrapper that avoids Spring WebFlux to resolve before rendering the HTML.
        // IReactiveDataDriverContextVariable

        IReactiveDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(measurements,
                1);

        model.addAttribute("measurements", dataDriver);

        return "reactive-template";

    }
}
