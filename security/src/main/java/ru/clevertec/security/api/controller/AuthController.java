package ru.clevertec.security.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.security.common.dto.request.SignInRequest;
import ru.clevertec.security.common.dto.request.SignUpRequest;
import ru.clevertec.security.common.dto.response.JwtAuthenticationResponse;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.core.service.authentication.AuthenticationService;

/**
 * Контроллер для аутентификации пользователей.
 * <p>
 * Этот контроллер предоставляет REST API для выполнения операций
 * аутентификации, включая регистрацию пользователей и вход в систему.
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AuthController {
    private final AuthenticationService authenticationService;

    /**
     * Регистрация нового пользователя.
     *
     * @param request объект, содержащий данные для регистрации пользователя
     * @return информация о зарегистрированном пользователе
     */
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoDTO signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    /**
     * Вход пользователя в систему.
     *
     * @param request объект, содержащий данные для входа пользователя
     * @return JWT токен аутентификации
     */
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
