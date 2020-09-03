package nl.jonathandegier.bank.services;

import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.repositories.AccountRepository;
import nl.jonathandegier.bank.repositories.PersonRepository;
import nl.jonathandegier.bank.services.exceptions.AccountNotFoundException;
import nl.jonathandegier.bank.services.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class AccountHolderService {

    private final AccountRepository accountRepository;
    private final PersonRepository personRepository;

    public AccountHolderService(AccountRepository accountRepository, PersonRepository personRepository) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
    }

    public void addAccountHolder(long accountId, long personId) {
        Account account = getAccount(accountId);
        Person person = getPerson(personId);

        account.addAccountHolder(person);
        person.addAccount(account);

        accountRepository.save(account);
        personRepository.save(person);
    }

    public void removeAccountHolder(long accountId, long personId) {
        Account account = getAccount(accountId);
        Person person = getPerson(personId);

        account.removeAccountHolder(person);
        person.removeAccount(account);

        accountRepository.save(account);
        personRepository.save(person);
    }

    private Account getAccount(long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotFoundException();
        }

        return optionalAccount.get();
    }

    private Person getPerson(long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isEmpty()) {
            throw new PersonNotFoundException();
        }

        return optionalPerson.get();
    }
}
