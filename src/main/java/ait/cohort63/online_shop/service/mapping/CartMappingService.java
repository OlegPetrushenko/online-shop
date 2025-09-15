package ait.cohort63.online_shop.service.mapping;

import ait.cohort63.online_shop.model.dto.CartDTO;
import ait.cohort63.online_shop.model.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMappingService {

    Cart mapDtoToEntity(CartDTO cartDTO);
    CartDTO mapEntityToDto(Cart cart);
}
