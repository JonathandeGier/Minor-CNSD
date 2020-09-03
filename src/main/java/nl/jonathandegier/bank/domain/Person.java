package nl.jonathandegier.bank.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @ManyToMany
    private Set<Account> accounts;

    public Person() {}
    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;

        this.accounts = new HashSet<>();
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
        return new ArrayList<>(this.accounts);
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
