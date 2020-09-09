package nl.jonathandegier.bank.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DummyApiService {

    private final RestTemplate template;

    public DummyApiService(RestTemplate template) {
        this.template = template;
    }

    public String getExternalEmployees() {
        return template.getForObject("http://dummy.restapiexample.com/api/v1/employees", String.class);
    }
}
