package ru.clevertec.security.core.service.authentication;


import ru.clevertec.security.common.dto.request.SignInRequest;
import ru.clevertec.security.common.dto.request.SignUpRequest;
import ru.clevertec.security.common.dto.response.JwtAuthenticationResponse;
import ru.clevertec.security.common.dto.user.UserInfoDTO;

public interface AuthenticationService {
    UserInfoDTO signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
