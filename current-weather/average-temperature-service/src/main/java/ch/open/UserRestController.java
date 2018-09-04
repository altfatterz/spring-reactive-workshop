package ch.open;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
public class UserRestController {

    @GetMapping("/")
    public Mono<Map<String, String>> hello(Mono<Principal> principal) {
        return principal
                .map(Principal::getName)
                .map(this::helloMessage);
    }

    private Map<String, String> helloMessage(String username) {
        return Collections.singletonMap("message", "Hello " + username + "!");
    }
}
