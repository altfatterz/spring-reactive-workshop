package ch.open.synchronousclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class PerformacenTestsSynchronousClient {

	public static void main(String[] args) {
		SpringApplication.run(PerformacenTestsSynchronousClient.class, args);
	}

	@GetMapping("/")
	public String endpoint(RestTemplate restTemplate) {
		return restTemplate.getForObject("http://localhost:9090", String.class);
	}
}
