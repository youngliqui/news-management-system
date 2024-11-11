package ru.clevertec.security.core.service.access;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.security.core.entity.User;
import ru.clevertec.security.core.service.authentication.JwtService;
import ru.clevertec.security.core.service.information.UserInformationService;

import java.util.List;
import java.util.Optional;

import static ru.clevertec.security.api.config.JwtAuthenticationFilter.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
public class AccessServiceImpl implements AccessService {
    private final UserInformationService userInformationService;
    private final JwtService jwtService;

    @Override
    public boolean checkUserAccess(String bearerToken, List<String> requiredRoles) {
        String token = bearerToken.substring(BEARER_PREFIX.length()).trim();

        if (!isValidToken(token)) {
            return false;
        }

        String username = jwtService.extractUsername(token);
        if (username == null || username.isEmpty()) {
            return false;
        }

        return Optional.ofNullable(userInformationService.getByUsername(username))
                .map(User::getRole)
                .map(role -> requiredRoles.stream().anyMatch(r -> r.equalsIgnoreCase(role.name())))
                .orElse(false);
    }

    private boolean isValidToken(String token) {
        return token != null && !token.isEmpty() &&
                jwtService.isTokenValid(token,
                        userInformationService.getByUsername(jwtService.extractUsername(token)));
    }
}
