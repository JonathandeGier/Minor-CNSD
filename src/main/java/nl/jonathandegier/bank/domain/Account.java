package nl.jonathandegier.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private long id;
    private String iban;
    private double saldo;
    private AccountStatus status;

    @JsonIgnore
    private List<Person> accountHolders;

    public Account(long id, String iban) {
        this.id = id;
        this.iban = iban;
        this.saldo = 0.0;
        this.status = AccountStatus.OPEN;

        this.accountHolders = new ArrayList<>();
    }

    public long getId() {
        return this.id;
    }

    public void block() {
        this.status = AccountStatus.BLOCKED;
    }

    public String getIban() {
        return iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void addAccountHolder(Person person) {
        this.accountHolders.add(person);
    }

    public void removeAccountHolder(Person person) {
        this.accountHolders.remove(person);
    }

    public List<Person> getAccountHolders() {
        return this.accountHolders;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Account) {
            Account other = (Account) obj;
            if (this.id == other.id) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
