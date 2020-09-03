package nl.jonathandegier.bank.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class PersonDTO {
    public long id;
    public String name;
    public String surname;

    @JsonIgnore
    public List<PlainAccountDTO> accounts;
}
