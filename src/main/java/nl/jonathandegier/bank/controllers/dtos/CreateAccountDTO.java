package nl.jonathandegier.bank.controllers.dtos;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAccountDTO {
    @Size
    @NotNull
    public String iban;
}
