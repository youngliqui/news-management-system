package ru.clevertec.security.core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.common.dto.user.UserUpdateDTO;
import ru.clevertec.security.core.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserInfoDTO userToUserInfoDTO(User user);

    void updateUser(@MappingTarget User user, UserUpdateDTO userUpdateDTO);
}
