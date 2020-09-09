package nl.jonathandegier.bank.services;

import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.repositories.AccountRepository;
import nl.jonathandegier.bank.services.exceptions.AccountNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAccounts() {
        return repository.findAll();
    }

    public Account getAccount(long id) {
        Optional<Account> account = repository.findById(id);
        if (account.isEmpty()) {
            throw new AccountNotFoundException();
        }

        return account.get();
    }

    public Account createAccount(String iban) {
        Account account = new Account(iban);
        return repository.save(account);
    }

    public Account blockAccount(long id) {
        Account account = getAccount(id);
        account.block();
        repository.save(account);

        return account;
    }

    public void deleteAccount(long id) {
        repository.delete(getAccount(id));
    }
}
