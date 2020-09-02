package nl.jonathandegier.bank.controllers.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateAccountDTO {
    @Min(value = 10000, message = "IBAN to short")
    @NotNull
    public String iban;
}
