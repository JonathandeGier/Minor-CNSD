package nl.jonathandegier.bank.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "iban", nullable = false)
    private String iban;

    @Column(name = "saldo", nullable = false)
    private double saldo;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToMany
    @JoinTable(
        name = "account_holders",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private Set<Person> accountHolders;

    public Account() {}
    public Account(String iban) {
        this.iban = iban;
        this.saldo = 0.0;
        this.status = AccountStatus.OPEN;

        this.accountHolders = new HashSet<>();
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
        return new ArrayList<>(accountHolders);
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
