package nl.jonathandegier.bank.controllers.mappers;

import nl.jonathandegier.bank.controllers.dtos.PersonDTO;
import nl.jonathandegier.bank.domain.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO toDto(Person person);
}
