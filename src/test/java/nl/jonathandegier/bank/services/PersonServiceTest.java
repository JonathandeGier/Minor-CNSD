package nl.jonathandegier.bank.services;

import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.repositories.PersonRepository;
import nl.jonathandegier.bank.services.exceptions.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repositoryMock;

    @InjectMocks
    private PersonService personService;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Test
    public void testGetPersons() {
        List<Person> expectedPersons = List.of(
                new Person("Elon", "Musk"),
                new Person("Bill", "Gates")
        );
        when(repositoryMock.findAll()).thenReturn(expectedPersons);

        var receivedPersons = personService.getPersons();

        assertEquals(expectedPersons, receivedPersons);
        verify(repositoryMock, times(1)).findAll();
    }

    @Test
    public void testGetPerson() {
        Person expectedPerson = new Person("Elon", "Musk");
        Optional<Person> mockReturn = Optional.of(expectedPerson);
        when(repositoryMock.findById(anyLong())).thenReturn(mockReturn);

        Person receivedPerson = personService.getPerson(1);

        assertEquals(expectedPerson, receivedPerson);
        verify(repositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void testGetPersonNotFound() {
        Optional<Person> mockReturn = Optional.empty();
        when(repositoryMock.findById(anyLong())).thenReturn(mockReturn);

        assertThrows(PersonNotFoundException.class, () -> {
            personService.getPerson(1);
        });
    }

    @Test
    public void testCreatePerson() {
        String name = "Elon";
        String surname = "Musk";
        Person expectedPerson = new Person("Elon", "Musk");
        when(repositoryMock.save(any(Person.class))).thenReturn(expectedPerson);

        Person receivedPerson = personService.createPerson(name, surname);

        assertEquals(expectedPerson.getName(), receivedPerson.getName());
        assertEquals(expectedPerson.getSurname(), receivedPerson.getSurname());
        verify(repositoryMock, times(1)).save(personCaptor.capture());
        Person savedPerson = personCaptor.getValue();

        assertEquals(name, savedPerson.getName());
        assertEquals(surname, savedPerson.getSurname());
    }

    @Test
    public void testDeletePerson() {
        Person expectedPerson = new Person("Elon", "Musk");
        Optional<Person> mockReturn = Optional.of(expectedPerson);
        when(repositoryMock.findById(anyLong())).thenReturn(mockReturn);

        personService.deletePerson(1);

        verify(repositoryMock, times(1)).findById(anyLong());
        verify(repositoryMock, times(1)).delete(personCaptor.capture());
        Person deletedPerson = personCaptor.getValue();

        assertEquals(expectedPerson, deletedPerson);
    }
}

