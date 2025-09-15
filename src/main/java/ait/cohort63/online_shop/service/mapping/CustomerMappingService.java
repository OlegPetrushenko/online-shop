package ait.cohort63.online_shop.service.mapping;

import ait.cohort63.online_shop.model.dto.CustomerDTO;
import ait.cohort63.online_shop.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CartMappingService.class)
public interface CustomerMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active",  constant = "true")
    Customer mapDtoToEntity(CustomerDTO customerDTO);

    CustomerDTO mapEntityToDto(Customer customer);
}
