package integration.controllers;

import nl.jonathandegier.bank.Main;
import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.services.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Main.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService serviceMock;

    @Test
    public void testgetAccounts() throws Exception {
        when(serviceMock.getAccounts()).thenReturn(List.of(
                new Account("12345"),
                new Account("23456"),
                new Account("34567")
        ));

        mvc.perform(get("/accounts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$", hasSize(3)));

        verify(serviceMock, times(1)).getAccounts();
    }

    @Test
    public void testGetAccount() throws Exception {
        when(serviceMock.getAccount(anyLong())).thenReturn(new Account("45678"));
        long id = 2;
        mvc.perform(get("/accounts/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.iban", is("45678")))
            .andExpect(jsonPath("$.saldo", is(0.0)))
            .andExpect(jsonPath("$.status", is("OPEN")));

        verify(serviceMock, times(1)).getAccount(id);
    }

    @Test
    public void testCreateAccount() throws Exception {
        String iban = "23456";
        when(serviceMock.createAccount(iban)).thenReturn(new Account(iban));

        mvc.perform(post("/accounts")
            .content("{\"iban\": \"" + iban + "\"}")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.iban", is(iban)));

        verify(serviceMock, times(1)).createAccount(iban);
    }

    @Test
    public void testBlockAccount() throws Exception {
        Answer<Account> answer = new Answer<Account>() {
            @Override
            public Account answer(InvocationOnMock invocationOnMock) throws Throwable {
                Account account = new Account("45678");
                account.block();
                return account;
            }
        };

        when(serviceMock.blockAccount(anyLong())).thenAnswer(answer);
        long id = 1;

        mvc.perform(put("/accounts/" + id + "/block"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.iban", is("45678")))
            .andExpect(jsonPath("$.status", is("BLOCKED")));

        verify(serviceMock, times(1)).blockAccount(id);
    }

    @Test
    public void testDeleteAccount() throws Exception {
        long id = 3;

        mvc.perform(delete("/accounts/" + id))
            .andExpect(status().isNoContent());

        verify(serviceMock, times(1)).deleteAccount(id);
    }

    @Test
    public void testAddAccountHolder() throws Exception {

    }

    @Test
    public void testRemoveAccountHolder() throws Exception {

    }
}
