package nl.jonathandegier.bank.services;

import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.repositories.PersonRepository;
import nl.jonathandegier.bank.services.exceptions.PersonNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getPersons() {
        return repository.findAll();
    }

    public Person getPerson(long id) {
        Optional<Person> person = repository.findById(id);

        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }

        return person.get();
    }

    public Person createPerson(String name, String surname) {
        Person person = new Person(name, surname);
        return repository.save(person);
    }

    public void deletePerson(long id) {
        repository.delete(getPerson(id));
    }
}
