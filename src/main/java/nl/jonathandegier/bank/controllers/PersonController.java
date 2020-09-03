package nl.jonathandegier.bank.controllers;

import nl.jonathandegier.bank.controllers.dtos.CreatePersonDTO;
import nl.jonathandegier.bank.controllers.dtos.PersonDTO;
import nl.jonathandegier.bank.controllers.mappers.PersonMapper;
import nl.jonathandegier.bank.services.AccountHolderService;
import nl.jonathandegier.bank.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("persons")
public class PersonController {

    private final PersonService service;
    private final AccountHolderService accountHolderService;
    private final PersonMapper mapper;

    public PersonController(PersonService service, AccountHolderService accountHolderService, PersonMapper mapper) {
        this.service = service;
        this.accountHolderService = accountHolderService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> getPersons(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "perPage", defaultValue = "5") int perPage) {
        int fromIndex = page * perPage;
        int toIndex = (page + 1) * perPage;

        return ResponseEntity.ok(
                service.getPersons()
                        .stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList()
                        ).subList(fromIndex, toIndex)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.getPerson(id)));
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody CreatePersonDTO dto) {
        PersonDTO account = mapper.toDto(service.createPerson(dto.name, dto.surname));
        return ResponseEntity.created(URI.create("/" + account.id)).body(account);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletePerson(@PathVariable long id) {
        service.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{personId}/{accountId}")
    public ResponseEntity<PersonDTO> addAccount(@PathVariable long personId, @PathVariable long accountId) {
        accountHolderService.addAccountHolder(accountId, personId);
        return ResponseEntity.ok(mapper.toDto(service.getPerson(accountId)));
    }

    @DeleteMapping("{personId}/{accountId}")
    public ResponseEntity<PersonDTO> removeAccount(@PathVariable long personId, @PathVariable long accountId) {
        accountHolderService.removeAccountHolder(accountId, personId);
        return ResponseEntity.ok(mapper.toDto(service.getPerson(accountId)));
    }
}
