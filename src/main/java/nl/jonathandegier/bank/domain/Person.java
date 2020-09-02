package nl.jonathandegier.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private long id;
    private String name;
    private String surname;

    @JsonIgnore
    private List<Account> accounts;

    public Person(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;

        this.accounts = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void removeAccount(Account account) {
        this.accounts.remove(account);
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person other = (Person) obj;
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
