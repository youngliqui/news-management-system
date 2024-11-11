package ru.clevertec.security.common.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.clevertec.security.common.role.Role;

@Getter
@Setter
public class UserInfoDTO {
    private Long id;
    private String username;
    private Role role;
}
