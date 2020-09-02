package nl.jonathandegier.bank.services;

import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.repositories.AccountRepository;
import nl.jonathandegier.bank.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;
    private final PersonRepository personRepository;

    public AccountService(AccountRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;

        createAccount("12345");
        createAccount("23456");
        createAccount("34567");
        createAccount("45678");
        createAccount("56789");
    }

    public List<Account> getAccounts() {
        return repository.getAllAccounts();
    }

    public Account getAccount(long id) {
        return repository.getAccount(id);
    }

    public Account createAccount(String iban) {
        long id = repository.getNextId();
        Account account = new Account(id, iban);

        repository.storeAccount(account);
        return account;
    }

    public void blockAccount(long id) {
        getAccount(id).block();
    }

    public void deleteAccount(long id) {
        repository.getAccount(id); // so it generates a 404 response if the account does not exist
        repository.deleteAccount(id);
    }

    public List<Person> getAccountHolders(long id) {
        return repository.getAccount(id).getAccountHolders();
    }

    public void addAccountHolder(long accountId, long personId) {
        Account account = repository.getAccount(accountId);
        Person person = personRepository.getPerson(personId);
        account.addAccountHolder(person);
        person.addAccount(account);
    }

    public void removeAccountHolder(long accountId, long personId) {
        Account account = repository.getAccount(accountId);
        Person person = personRepository.getPerson(personId);
        account.removeAccountHolder(person);
        person.removeAccount(account);
    }
}
