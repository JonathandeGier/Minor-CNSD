package nl.jonathandegier.bank.services;


import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.domain.AccountStatus;
import nl.jonathandegier.bank.repositories.AccountRepository;
import nl.jonathandegier.bank.services.exceptions.AccountNotFoundException;
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
public class AccountServiceTest {

    @Mock
    private AccountRepository repositoryMock;

    @InjectMocks
    private AccountService accountService;

    @Captor
    private ArgumentCaptor<Account> accountCaptor;

    @Test
    public void testGetAccounts() {
        List<Account> expectedAccounts = List.of(
                new Account("12345"),
                new Account("23456")
        );
        when(repositoryMock.findAll()).thenReturn(expectedAccounts);

        var receivedAccounts = accountService.getAccounts();

        assertEquals(expectedAccounts, receivedAccounts);
        verify(repositoryMock, times(1)).findAll();
    }

    @Test
    public void testGetAccount() {
        Account expectedAccount = new Account("1234");
        Optional<Account> mockReturn = Optional.of(expectedAccount);
        when(repositoryMock.findById(anyLong())).thenReturn(mockReturn);

        Account receivedAccount = accountService.getAccount(1);

        assertEquals(expectedAccount, receivedAccount);
        verify(repositoryMock, times(1)).findById(anyLong());
    }

    @Test
    public void testGetAccountNotFound() {
        Optional<Account> mockReturn = Optional.empty();
        when(repositoryMock.findById(anyLong())).thenReturn(mockReturn);

        assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccount(1);
        });
    }

    @Test
    public void testCreateAccount() {
        String iban = "34567";
        Account expectedAccount = new Account(iban);
        when(repositoryMock.save(any(Account.class))).thenReturn(expectedAccount);

        Account receivedAccount = accountService.createAccount(iban);

        assertEquals(expectedAccount.getIban(), receivedAccount.getIban());
        verify(repositoryMock, times(1)).save(accountCaptor.capture());
        Account sendAccount = accountCaptor.getValue();

        assertEquals(iban, sendAccount.getIban());
        assertEquals(0.0, sendAccount.getSaldo());
        assertEquals(AccountStatus.OPEN, sendAccount.getStatus());
    }

    @Test
    public void testBlockAccount() {
        Account expectedAccount = new Account("1234");
        Optional<Account> mockReturn = Optional.of(expectedAccount);
        when(repositoryMock.findById(anyLong())).thenReturn(mockReturn);

        accountService.blockAccount(1);

        verify(repositoryMock, times(1)).findById(anyLong());
        verify(repositoryMock, times(1)).save(accountCaptor.capture());

        Account savedAccount = accountCaptor.getValue();
        assertEquals(AccountStatus.BLOCKED, savedAccount.getStatus());
    }

    @Test
    public void testDeleteAccount() {
        Account expectedAccount = new Account("1234");
        Optional<Account> mockReturn = Optional.of(expectedAccount);
        when(repositoryMock.findById(anyLong())).thenReturn(mockReturn);

        accountService.deleteAccount(1);

        verify(repositoryMock, times(1)).findById(anyLong());
        verify(repositoryMock, times(1)).delete(accountCaptor.capture());
        Account deletedAccount = accountCaptor.getValue();

        assertEquals(expectedAccount, deletedAccount);
    }
}
