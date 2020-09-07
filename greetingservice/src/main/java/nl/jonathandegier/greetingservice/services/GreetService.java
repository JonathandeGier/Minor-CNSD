package nl.jonathandegier.greetingservice.services;

import nl.jonathandegier.greetingservice.clients.NameClient;
import org.springframework.stereotype.Service;

@Service
public class GreetService {

    private final NameClient client;

    public GreetService(NameClient client) {
        this.client = client;
    }

    public String greet() {
        return "Hello " + client.getName() + "!";
    }
}
