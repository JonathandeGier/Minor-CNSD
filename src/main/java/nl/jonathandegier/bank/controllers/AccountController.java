package nl.jonathandegier.bank.controllers;

import nl.jonathandegier.bank.controllers.dtos.AccountDTO;
import nl.jonathandegier.bank.controllers.dtos.CreateAccountDTO;
import nl.jonathandegier.bank.controllers.mappers.AccountMapper;
import nl.jonathandegier.bank.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService service;
    private final AccountMapper mapper;

    public AccountController(AccountService service, AccountMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "perPage", defaultValue = "5") int perPage) {
        int fromIndex = page * perPage;
        int toIndex = (page + 1) * perPage;

        return ResponseEntity.ok(
            service.getAccounts()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList()
            ).subList(fromIndex, toIndex)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.getAccount(id)));
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody CreateAccountDTO dto) {
        AccountDTO account = mapper.toDto(service.createAccount(dto.iban));
        return ResponseEntity.created(URI.create("/" + account.id)).body(account);
    }

    @PutMapping("{id}/block")
    public ResponseEntity<AccountDTO> blockAccount(@PathVariable long id) {
        service.blockAccount(id);
        return ResponseEntity.ok(mapper.toDto(service.getAccount(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAccount(@PathVariable long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{accountId}/{personId}")
    public ResponseEntity<AccountDTO> addAccountHolder(@PathVariable long accountId, @PathVariable long personId) {
        service.addAccountHolder(accountId, personId);
        return ResponseEntity.ok(mapper.toDto(service.getAccount(accountId)));
    }

    @DeleteMapping("{accountId}/{personId}")
    public ResponseEntity<AccountDTO> removeAccountHolder(@PathVariable long accountId, @PathVariable long personId) {
        service.removeAccountHolder(accountId, personId);
        return ResponseEntity.ok(mapper.toDto(service.getAccount(accountId)));
    }
}
