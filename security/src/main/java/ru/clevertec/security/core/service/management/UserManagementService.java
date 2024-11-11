package ru.clevertec.security.core.service.management;

import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.common.dto.user.UserUpdateDTO;
import ru.clevertec.security.core.entity.User;

public interface UserManagementService {
    UserInfoDTO save(User user);

    UserInfoDTO create(User user);

    UserInfoDTO updateUser(Long userId, UserUpdateDTO updateDTO);
}
