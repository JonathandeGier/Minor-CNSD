package nl.quintor._security.model.mapper;

import nl.quintor._security.model.domain.Address;
import nl.quintor._security.model.dto.AddressDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper extends ModelToDTOMapper<Address, AddressDTO> {
}
