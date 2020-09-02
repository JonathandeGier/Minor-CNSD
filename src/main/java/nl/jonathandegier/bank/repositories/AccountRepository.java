package nl.jonathandegier.bank.repositories;

import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.repositories.exceptions.AccountNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountRepository {

    private Map<Long, Account> accounts;
    private long nextId;

    public AccountRepository() {
        this.accounts = new HashMap<>();
        this.nextId = 0;
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(this.accounts.values());
    }

    public Account getAccount(long id) {
        Account account = this.accounts.get(id);
        if (account != null) {
            return account;
        } else {
            throw new AccountNotFoundException();
        }
    }

    public boolean storeAccount(Account account) {
        return this.accounts.put(account.getId(), account) != null;
    }

    public void deleteAccount(long id) {
        this.accounts.remove(id);
    }

    public long getNextId() {
        this.nextId++;
        return this.nextId;
    }
}
