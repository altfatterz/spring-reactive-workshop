package ch.open;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;

@Controller
class CustomersViewController {

    private final CustomerRepository customerRepository;

    public CustomersViewController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(value = "/customers-view")
    Rendering customers() {
        return Rendering.view("customers")
                .modelAttribute("customers", customerRepository.findAll())
                .build();
    }

}
