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

/**
 * Контроллер для управления пользователями.
 * <p>
 * Этот контроллер предоставляет REST API для выполнения операций
 * над пользователями, включая получение информации о пользователях,
 * обновление данных и управление доступом.
 * </p>
 */
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserInformationService userInformationService;
    private final UserManagementService userManagementService;
    private final UserAuthenticationService userAuthenticationService;
    private final AccessService accessService;

    /**
     * Получить всех пользователей с пагинацией.
     *
     * @param page номер страницы (начиная с 0)
     * @param size количество пользователей на странице
     * @return страница пользователей
     */
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<UserInfoDTO> getAllUsers(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return userInformationService.getAllUsers(pageable);
    }

    /**
     * Получить информацию о пользователе по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return информация о пользователе
     */
    @GetMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasAnyAuthority('ADMIN')")
    public UserInfoDTO getUserInfoById(@PathVariable Long userId) {
        return userInformationService.getUserInfoById(userId);
    }

    /**
     * Обновить данные пользователя.
     *
     * @param userId    идентификатор пользователя
     * @param updateDTO данные для обновления пользователя
     * @return обновленная информация о пользователе
     */
    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasAnyAuthority('ADMIN')")
    public UserInfoDTO updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateDTO updateDTO
    ) {
        return userManagementService.updateUser(userId, updateDTO);
    }

    /**
     * Изменить пароль пользователя.
     *
     * @param userId            идентификатор пользователя
     * @param changePasswordDTO данные для изменения пароля
     * @return информация о пользователе после изменения пароля
     */
    @PatchMapping("/{userId}/password")
    @PreAuthorize("#userId == authentication.principal.id or hasAnyAuthority('ADMIN')")
    public UserInfoDTO updateUserPassword(
            @PathVariable Long userId,
            @RequestBody @Valid ChangePasswordDTO changePasswordDTO
    ) {
        return userAuthenticationService.changePassword(userId, changePasswordDTO);
    }

    /**
     * Проверить доступ пользователя по токену и ролям.
     *
     * @param bearerToken токен авторизации пользователя
     * @param roles       список ролей для проверки доступа
     * @return true, если у пользователя есть хотя бы одна из указанных ролей; иначе false
     */
    @GetMapping("/access")
    public Boolean checkAccess(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam List<String> roles
    ) {
        return accessService.checkUserAccess(bearerToken, roles);
    }
}
