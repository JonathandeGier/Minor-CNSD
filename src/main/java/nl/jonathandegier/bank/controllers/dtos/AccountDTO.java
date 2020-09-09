package nl.jonathandegier.bank.controllers.dtos;

import lombok.ToString;

import java.util.List;

@ToString
public class AccountDTO {
    public long id;
    public String iban;
    public double saldo;
    public String status;

    public List<PlainPersonDTO> accountHolders;
}
