package nl.jonathandegier.bank.controllers;

import nl.jonathandegier.bank.controllers.dtos.AccountDTO;
import nl.jonathandegier.bank.controllers.mappers.AccountMapper;
import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.services.AccountHolderService;
import nl.jonathandegier.bank.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountServiceMock;

    @Mock
    private AccountHolderService accountHolderServiceMock;

    @Mock
    private AccountMapper accountMapperMock;

    @InjectMocks
    private AccountController controller;

    @BeforeEach
    public void initMapper() {
        Answer<AccountDTO> answer = new Answer<AccountDTO>() {
            @Override
            public AccountDTO answer(InvocationOnMock invocationOnMock) throws Throwable {
                Account account = invocationOnMock.getArgument(0, Account.class);
                AccountDTO dto = new AccountDTO();
                dto.id = account.getId();
                dto.iban = account.getIban();
                dto.saldo = account.getSaldo();
                dto.status = account.getStatus().toString();

                return dto;
            }
        };

        when(accountMapperMock.toDto(any(Account.class))).thenAnswer(answer);
    }

    @Test
    public void testGetAccounts() {
        List<Account> accounts = List.of(
                new Account("12345"),
                new Account("23456")
        );
        when(accountServiceMock.getAccounts()).thenReturn(accounts);

        var response = controller.getAccounts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("12345", response.getBody().get(0).iban);
        assertEquals("23456", response.getBody().get(1).iban);
    }

    @Test
    public void testGetAccount() {
        Account account = new Account("123456");
        when(accountServiceMock.getAccount(anyLong())).thenReturn(account);

        var response = controller.getAccount(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(account.getId(), response.getBody().id);
        assertEquals(account.getIban(), response.getBody().iban);
        assertEquals(account.getSaldo(), response.getBody().saldo);
        assertEquals("OPEN", response.getBody().status);
    }

//    @Test
//    public void testCreateAccount() {
//    }
//
//    @Test
//    public void testBlockAccount() {
//    }
//
//    @Test
//    public void testDeleteAccount() {
//    }
//
//    @Test
//    public void testAddAccountHolder() {
//    }
//
//    @Test
//    public void testRemoveAccountHolder() {
//    }
}
