package ch.open.temperaturedashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class FooController {

    @GetMapping("/")
    public String fooStream(final Model model) {
        Flux fooStream = Flux.just("foo", "bar", "baz");
        model.addAttribute("fooStream", fooStream);
        return "index";
    }


}
