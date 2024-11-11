package ru.clevertec.security.core.service.authentication;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.security.common.dto.request.SignInRequest;
import ru.clevertec.security.common.dto.request.SignUpRequest;
import ru.clevertec.security.common.dto.response.JwtAuthenticationResponse;
import ru.clevertec.security.common.dto.user.UserInfoDTO;
import ru.clevertec.security.common.role.Role;
import ru.clevertec.security.core.entity.User;
import ru.clevertec.security.core.mapper.UserMapper;
import ru.clevertec.security.core.service.authentication.user.UserAuthenticationService;
import ru.clevertec.security.core.service.management.UserManagementService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserAuthenticationService userAuthenticationService;
    private final UserManagementService userManagementService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserInfoDTO signUp(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.SUBSCRIBER)
                .build();

        userManagementService.create(user);

        return UserMapper.INSTANCE.userToUserInfoDTO(user);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user = userAuthenticationService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
