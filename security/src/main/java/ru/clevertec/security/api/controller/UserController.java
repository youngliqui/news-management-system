package ru.clevertec.security.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.security.common.dto.user.ChangePasswordDTO;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.common.dto.user.UserUpdateDTO;
import ru.clevertec.security.core.service.access.AccessService;
import ru.clevertec.security.core.service.authentication.user.UserAuthenticationService;
import ru.clevertec.security.core.service.information.UserInformationService;
import ru.clevertec.security.core.service.management.UserManagementService;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserInformationService userInformationService;
    private final UserManagementService userManagementService;
    private final UserAuthenticationService userAuthenticationService;
    private final AccessService accessService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<UserInfoDTO> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return userInformationService.getAllUsers(pageable);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasAnyAuthority('ADMIN')")
    public UserInfoDTO getUserInfoById(@PathVariable Long userId) {
        return userInformationService.getUserInfoById(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasAnyAuthority('ADMIN')")
    public UserInfoDTO updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateDTO updateDTO
    ) {
        return userManagementService.updateUser(userId, updateDTO);
    }

    @PatchMapping("/{userId}/password")
    @PreAuthorize("#userId == authentication.principal.id or hasAnyAuthority('ADMIN')")
    public UserInfoDTO updateUserPassword(
            @PathVariable Long userId,
            @RequestBody @Valid ChangePasswordDTO changePasswordDTO
    ) {
        return userAuthenticationService.changePassword(userId, changePasswordDTO);
    }

    @GetMapping("/access")
    public Boolean checkAccess(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam List<String> roles
    ) {
        return accessService.checkUserAccess(bearerToken, roles);
    }
}
