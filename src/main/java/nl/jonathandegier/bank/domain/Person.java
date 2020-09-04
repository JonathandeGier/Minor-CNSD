package nl.jonathandegier.bank.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Person extends BaseEntity {

    @Column(name = "name", nullable = false)
    @Getter
    private String name;

    @Column(name = "surname", nullable = false)
    @Getter
    private String surname;

    @ManyToMany(mappedBy = "accountHolders")
    private Set<Account> accounts;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;

        this.accounts = new HashSet<>();
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
}
