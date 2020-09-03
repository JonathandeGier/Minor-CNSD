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

        createAccount("12345");
        createAccount("23456");
        createAccount("34567");
        createAccount("45678");
        createAccount("56789");
        createAccount("67890");
        createAccount("78901");
        createAccount("89012");
        createAccount("90123");
        createAccount("01234");
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

    public void blockAccount(long id) {
        Account account = getAccount(id);
        account.block();
        repository.save(account);
    }

    public void deleteAccount(long id) {
        repository.delete(getAccount(id));
    }
}
