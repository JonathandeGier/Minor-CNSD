package nl.jonathandegier.greetingservice.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import nl.jonathandegier.greetingservice.services.GreetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("greet")
public class GreetingController {

    private final GreetService service;

    public GreetingController(GreetService service) {
        this.service = service;
    }

    @GetMapping
//    @HystrixCommand(fallbackMethod = "greetFallback")
    public String greet() {
        return service.greet();
    }

    public String greetFallback() {
        return "No one to greet... There is no one here";
    }
}
