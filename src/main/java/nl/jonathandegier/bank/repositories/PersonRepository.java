package nl.jonathandegier.bank.repositories;

import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.services.exceptions.PersonNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

//    private Map<Long, Person> persons;
//    private long nextId;
//
//    public PersonRepository() {
//        this.persons = new HashMap<>();
//        this.nextId = 0;
//    }
//
//    public List<Person> getAllPersons() {
//        return new ArrayList<>(this.persons.values());
//    }
//
//    public Person getPerson(long id) {
//        Person person = this.persons.get(id);
//        if (person != null) {
//            return person;
//        } else {
//            throw new PersonNotFoundException();
//        }
//    }
//
//    public boolean storePerson(Person person) {
//        return this.persons.put(person.getId(), person) != null;
//    }
//
//    public void deletePerson(long id) {
//        this.persons.remove(id);
//    }
//
//    public long getNextId() {
//        this.nextId++;
//        return this.nextId;
//    }
}
