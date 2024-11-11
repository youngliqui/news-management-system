package ru.clevertec.security.core.service.information;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.core.entity.User;

public interface UserInformationService {
    Page<UserInfoDTO> getAllUsers(Pageable pageable);

    UserInfoDTO getUserInfoById(Long userId);

    User getByUsername(String username);
}
