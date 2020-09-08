package nl.jonathandegier.bank.services;

import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.domain.Person;
import nl.jonathandegier.bank.repositories.AccountRepository;
import nl.jonathandegier.bank.repositories.PersonRepository;
import nl.jonathandegier.bank.services.exceptions.AccountNotFoundException;
import nl.jonathandegier.bank.services.exceptions.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountHolderTest {

    @Mock
    private AccountRepository accountRepositoryMock;

    @Mock
    private PersonRepository personRepositoryMock;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @InjectMocks
    private AccountHolderService service;

    @Test
    public void testAddAccountHolder() {
        Account account = new Account("12345");
        Person person = new Person("Elon", "Musk");
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.of(account));
        when(personRepositoryMock.findById(anyLong())).thenReturn(Optional.of(person));

        service.addAccountHolder(1, 1);

        verify(accountRepositoryMock, times(1)).findById(anyLong());
        verify(personRepositoryMock, times(1)).findById(anyLong());
        verify(accountRepositoryMock, times(1)).save(accountCaptor.capture());
        verify(personRepositoryMock, times(1)).save(personCaptor.capture());

        Account savedAccount = accountCaptor.getValue();
        Person savedPerson = personCaptor.getValue();

        assertEquals(savedAccount, savedPerson.getAccounts().get(0));
        assertEquals(savedPerson, savedAccount.getAccountHolders().get(0));
    }

    @Test
    public void testAddAccountHolderAccountNotFound() {
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            service.addAccountHolder(1, 1);
        });
    }

    @Test
    public void testAddAccountHolderPersonNotFound() {
        Account account = new Account("12345");
        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.of(account));
        when(personRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> {
            service.addAccountHolder(1, 1);
        });
    }

    @Test
    public void testAddAccountHolderAlreadyAdded() {
        Account account = new Account("12345");
        Person person = new Person("Elon", "Musk");
        account.addAccountHolder(person);
        person.addAccount(account);

        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.of(account));
        when(personRepositoryMock.findById(anyLong())).thenReturn(Optional.of(person));

        service.addAccountHolder(1, 1);

        verify(accountRepositoryMock, times(1)).findById(anyLong());
        verify(personRepositoryMock, times(1)).findById(anyLong());
        verify(accountRepositoryMock, times(1)).save(accountCaptor.capture());
        verify(personRepositoryMock, times(1)).save(personCaptor.capture());

        Account savedAccount = accountCaptor.getValue();
        Person savedPerson = personCaptor.getValue();

        assertEquals(savedAccount, savedPerson.getAccounts().get(0));
        assertEquals(savedPerson, savedAccount.getAccountHolders().get(0));
    }

    @Test
    public void testRemoveAccountHolder() {
        Account account = new Account("12345");
        Person person = new Person("Elon", "Musk");
        account.addAccountHolder(person);
        person.addAccount(account);

        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.of(account));
        when(personRepositoryMock.findById(anyLong())).thenReturn(Optional.of(person));

        service.removeAccountHolder(1, 1);

        verify(accountRepositoryMock, times(1)).findById(anyLong());
        verify(personRepositoryMock, times(1)).findById(anyLong());
        verify(accountRepositoryMock, times(1)).save(accountCaptor.capture());
        verify(personRepositoryMock, times(1)).save(personCaptor.capture());

        Account savedAccount = accountCaptor.getValue();
        Person savedPerson = personCaptor.getValue();

        assertTrue(savedAccount.getAccountHolders().isEmpty());
        assertTrue(savedPerson.getAccounts().isEmpty());
    }

    @Test
    public void testRemoveAccountHolderAccountNotFound() {
        Account account = new Account("12345");
        Person person = new Person("Elon", "Musk");
        account.addAccountHolder(person);
        person.addAccount(account);

        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> {
            service.removeAccountHolder(1, 1);
        });
    }

    @Test
    public void testRemoveAccountHolderPersonNotFound() {
        Account account = new Account("12345");
        Person person = new Person("Elon", "Musk");
        account.addAccountHolder(person);
        person.addAccount(account);

        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.of(account));
        when(personRepositoryMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> {
            service.removeAccountHolder(1, 1);
        });
    }

    @Test
    public void testRemoveAccountHolderAlreadyRemoved() {
        Account account = new Account("12345");
        Person person = new Person("Elon", "Musk");

        when(accountRepositoryMock.findById(anyLong())).thenReturn(Optional.of(account));
        when(personRepositoryMock.findById(anyLong())).thenReturn(Optional.of(person));

        service.removeAccountHolder(1, 1);

        verify(accountRepositoryMock, times(1)).findById(anyLong());
        verify(personRepositoryMock, times(1)).findById(anyLong());
        verify(accountRepositoryMock, times(1)).save(accountCaptor.capture());
        verify(personRepositoryMock, times(1)).save(personCaptor.capture());

        Account savedAccount = accountCaptor.getValue();
        Person savedPerson = personCaptor.getValue();

        assertTrue(savedAccount.getAccountHolders().isEmpty());
        assertTrue(savedPerson.getAccounts().isEmpty());
    }
}
