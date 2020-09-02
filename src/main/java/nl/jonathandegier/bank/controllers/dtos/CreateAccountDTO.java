package nl.jonathandegier.bank.controllers.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class CreateAccountDTO {
    @Length(min = 5, message = "IBAN to short")
    @NotNull
    public String iban;
}
