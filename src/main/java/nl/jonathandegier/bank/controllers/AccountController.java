package nl.jonathandegier.bank.controllers;

import nl.jonathandegier.bank.controllers.dtos.CreateAccountDTO;
import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAccounts(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "perPage", defaultValue = "5") int perPage) {
        int fromIndex = page * perPage;
        int toIndex = (page + 1) * perPage;
        return ResponseEntity.ok(service.getAccounts().subList(fromIndex, toIndex));
    }

    @GetMapping("{id}")
    public ResponseEntity<Account> getAccount(@PathVariable long id) {
        return ResponseEntity.ok(service.getAccount(id));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountDTO dto) {
        Account account = service.createAccount(dto.iban);
        return ResponseEntity.created(URI.create("/" + account.getId())).body(account);
    }

    @PutMapping("{id}/block")
    public ResponseEntity<Account> blockAccount(@PathVariable long id) {
        service.blockAccount(id);
        return ResponseEntity.ok(service.getAccount(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAccount(@PathVariable long id) {
        service.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/accountHolders")
    public ResponseEntity<List<Person>> getAccountHolders(@PathVariable long id) {
        return ResponseEntity.ok(service.getAccountHolders(id));
    }

    @PostMapping("{accountId}/{personId}")
    public ResponseEntity<Account> addAccountHolder(@PathVariable long accountId, @PathVariable long personId) {
        service.addAccountHolder(accountId, personId);
        return ResponseEntity.ok(service.getAccount(accountId));
    }

    @DeleteMapping("{accountId}/{personId}")
    public ResponseEntity<Account> removeAccountHolder(@PathVariable long accountId, @PathVariable long personId) {
        service.removeAccountHolder(accountId, personId);
        return ResponseEntity.ok(service.getAccount(accountId));
    }
}
