package com.ufro.microservice.authentication_service.mapper;

import com.ufro.microservice.authentication_service.dto.UserCrendentialDTO;
import com.ufro.microservice.authentication_service.dto.UserLoginDTO;
import com.ufro.microservice.authentication_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    User toModel(UserLoginDTO dto);
    UserLoginDTO toLoginDTO(User user);

    User registerToModel(UserCrendentialDTO dto);
    UserCrendentialDTO registerToDTO(User user);

}
