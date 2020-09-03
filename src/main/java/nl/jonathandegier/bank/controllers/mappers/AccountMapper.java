package nl.jonathandegier.bank.controllers.mappers;

import nl.jonathandegier.bank.controllers.dtos.AccountDTO;
import nl.jonathandegier.bank.domain.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO toDto(Account account);
}
