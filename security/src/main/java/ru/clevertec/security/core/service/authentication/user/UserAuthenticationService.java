package ru.clevertec.security.core.service.authentication.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.clevertec.security.common.dto.user.ChangePasswordDTO;
import ru.clevertec.security.common.dto.user.UserInfoDTO;


public interface UserAuthenticationService {
    UserInfoDTO changePassword(Long userId, ChangePasswordDTO changePasswordDTO);

    UserDetailsService userDetailsService();
}
