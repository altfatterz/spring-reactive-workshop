package ch.open.temperaturedashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeasurementController {

    @GetMapping("/measurements")
    public String measurements() {
        return "measurements";
    }

}
