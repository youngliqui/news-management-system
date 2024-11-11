package ru.clevertec.security.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.security.common.dto.request.SignInRequest;
import ru.clevertec.security.common.dto.request.SignUpRequest;
import ru.clevertec.security.common.dto.response.JwtAuthenticationResponse;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.core.service.authentication.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoDTO signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
