package ait.cohort63.online_shop.service.mapping;

import ait.cohort63.online_shop.model.dto.CartDTO;
import ait.cohort63.online_shop.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMappingService {

    // тут НЕТ uses = CustomerMappingService.class

    // Чтобы не тащить обратно Customer и не зациклиться:
    @Mapping(target = "customer", ignore = true)
    Cart mapDtoToEntity(CartDTO cartDTO);

    // и в DTO тоже не уводим вглубь
    @Mapping(target = "customer", ignore = true)
    CartDTO mapEntityToDto(Cart cart);
}
