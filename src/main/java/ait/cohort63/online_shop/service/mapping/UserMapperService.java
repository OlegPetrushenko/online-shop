package ait.cohort63.online_shop.service.mapping;

import ait.cohort63.online_shop.model.dto.UserRegisterDTO;
import ait.cohort63.online_shop.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapperService {

    @Mapping(target = "id", ignore = true)
    User mapDtoToEntity(UserRegisterDTO dto);
}
