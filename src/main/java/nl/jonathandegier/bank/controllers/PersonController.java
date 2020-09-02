package nl.jonathandegier.bank.controllers;

import nl.jonathandegier.bank.controllers.dtos.CreatePersonDTO;
import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAccounts() {
        return ResponseEntity.ok(service.getPersons());
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getAccount(@PathVariable long id) {
        return ResponseEntity.ok(service.getPerson(id));
    }

    @PostMapping
    public ResponseEntity<Person> createAccount(@Valid @RequestBody CreatePersonDTO dto) {
        Person account = service.createPerson(dto.name, dto.surname);
        return ResponseEntity.created(URI.create("/" + account.getId())).body(account);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAccount(@PathVariable long id) {
        service.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<List<Account>> getAccountHolders(@PathVariable long id) {
        return ResponseEntity.ok(service.getAccounts(id));
    }

    @PostMapping("{personId}/{accountId}")
    public ResponseEntity<Person> addAccountHolder(@PathVariable long personId, @PathVariable long accountId) {
        service.addAccount(accountId, personId);
        return ResponseEntity.ok(service.getPerson(accountId));
    }

    @DeleteMapping("{personId}/{accountId}")
    public ResponseEntity<Person> removeAccountHolder(@PathVariable long personId, @PathVariable long accountId) {
        service.removeAccount(accountId, personId);
        return ResponseEntity.ok(service.getPerson(accountId));
    }
}
