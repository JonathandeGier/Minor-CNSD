package nl.jonathandegier.bank.controllers;

import nl.jonathandegier.bank.services.DummyApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dummy")
public class DummyApiController {

    private final DummyApiService service;

    public DummyApiController(DummyApiService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> getEmployees() {
        return ResponseEntity.ok(service.getExternalEmployees());
    }
}
