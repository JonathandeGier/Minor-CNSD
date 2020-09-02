package nl.jonathandegier.bank.services;

import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.repositories.AccountRepository;
import nl.jonathandegier.bank.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final AccountRepository accountRepository;

    public PersonService(PersonRepository repository, AccountRepository accountRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;

        createPerson("Elon", "Musk");
        createPerson("Bill", "Gates");
        createPerson("Jeff", "Bezos");
    }

    public List<Person> getPersons() {
        return repository.getAllPersons();
    }

    public Person getPerson(long id) {
        return repository.getPerson(id);
    }

    public Person createPerson(String name, String surname) {
        long id = repository.getNextId();
        Person account = new Person(id, name, surname);

        repository.storePerson(account);
        return account;
    }

    public void deletePerson(long id) {
        repository.getPerson(id); // so it generates a 404 response if the person does not exist
        repository.deletePerson(id);
    }

    public List<Account> getAccounts(long id) {
        return repository.getPerson(id).getAccounts();
    }

    public void addAccount(long accountId, long personId) {
        Person person = repository.getPerson(personId);
        Account account = accountRepository.getAccount(accountId);
        person.addAccount(account);
        account.addAccountHolder(person);
    }

    public void removeAccount(long accountId, long personId) {
        Person person = repository.getPerson(personId);
        Account account = accountRepository.getAccount(accountId);
        person.removeAccount(account);
        account.removeAccountHolder(person);
    }
}
