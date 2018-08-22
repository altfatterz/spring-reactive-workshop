package ch.open.temperaturedashboard;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

@Controller
public class Events2Controller {

    @GetMapping("/events2")
    public String events() {
        return "events2";
    }

    @RequestMapping(value = "/events2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<String> events2Stream() {
        return Flux.interval(Duration.ofSeconds(2)).map(i -> UUID.randomUUID().toString());
    }
}
