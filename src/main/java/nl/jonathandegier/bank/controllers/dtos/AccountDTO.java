package nl.jonathandegier.bank.controllers.dtos;

import java.util.List;

public class AccountDTO {
    public long id;
    public String iban;
    public double saldo;
    public String status;

    public List<PlainPersonDTO> accountHolders;
}
