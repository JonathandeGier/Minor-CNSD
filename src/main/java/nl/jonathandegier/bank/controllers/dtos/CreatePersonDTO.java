package nl.jonathandegier.bank.controllers.dtos;

import javax.validation.constraints.NotNull;

public class CreatePersonDTO {
    @NotNull
    public String name;
    @NotNull
    public String surname;
}
