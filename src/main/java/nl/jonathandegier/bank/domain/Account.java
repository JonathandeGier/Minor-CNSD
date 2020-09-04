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
public class Account extends BaseEntity {

    @Column(name = "iban", nullable = false)
    @Getter
    private String iban;

    @Column(name = "saldo", nullable = false)
    @Getter
    private double saldo;

    @Enumerated(EnumType.STRING)
    @Getter
    private AccountStatus status;

    @ManyToMany
    @JoinTable(
        name = "account_holders",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> accountHolders;

    public Account(String iban) {
        this.iban = iban;
        this.saldo = 0.0;
        this.status = AccountStatus.OPEN;

        this.accountHolders = new HashSet<>();
    }

    public void block() {
        this.status = AccountStatus.BLOCKED;
    }

    public void addAccountHolder(Person person) {
        this.accountHolders.add(person);
    }

    public void removeAccountHolder(Person person) {
        this.accountHolders.remove(person);
    }

    public List<Person> getAccountHolders() {
        return new ArrayList<>(accountHolders);
    }
}
