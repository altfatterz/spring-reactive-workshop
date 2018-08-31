package ch.open.slowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
@RestController
public class SlowService {

	public static void main(String[] args) {
		SpringApplication.run(SlowService.class, args);
	}

	@GetMapping("/")
	public Mono<String> slow() {
		return Mono.delay(Duration.ofSeconds(5)).map(aLong -> UUID.randomUUID().toString());
	}
}
