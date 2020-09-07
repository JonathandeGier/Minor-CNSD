package nl.jonathandegier.userservice.controllers;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import nl.jonathandegier.userservice.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    @HystrixCommand(fallbackMethod = "getRandomNameFallback")
    public String getRandomName() {
        return service.getRandomName();
    }

    public String getRandomNameFallback() {
        return "How rude... no response 😫";
    }
}
