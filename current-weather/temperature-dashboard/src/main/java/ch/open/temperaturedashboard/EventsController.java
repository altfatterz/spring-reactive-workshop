package ch.open.temperaturedashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@Controller
public class EventsController {

    @RequestMapping("/events")
    public String events(final Model model) {

        Flux<String> stream = Flux.interval(Duration.ofSeconds(2)).map(i -> UUID.randomUUID().toString());

        // Creates a new lazy context variable, wrapping a reactive asynchronous data stream
        // and specifying a buffer size.
        // Sets Thymeleaf in data-driven mode in order to produce (render) Server-Sent Events
        // as the Flux produces values.
        // This object works as a wrapper that avoids Spring WebFlux to resolve before rendering the HTML.
        // IReactiveDataDriverContextVariable

        IReactiveDataDriverContextVariable dataDriver = new ReactiveDataDriverContextVariable(stream,
                1);

        model.addAttribute("uuids", dataDriver);

        return "events";

    }


}
