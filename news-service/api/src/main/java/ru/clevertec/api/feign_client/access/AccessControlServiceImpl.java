package ru.clevertec.api.feign_client.access;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.api.feign_client.UserServiceClient;
import ru.clevertec.api.feign_client.exception.AccessDeniedException;

import java.util.List;

/**
 * Реализация сервиса управления доступом.
 * <p>
 * Этот класс реализует интерфейс {@link AccessControlService} и предоставляет
 * логику для проверки доступа пользователей на основе их токенов и ролей.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AccessControlServiceImpl implements AccessControlService {
    private final UserServiceClient userServiceClient;

    /**
     * Проверяет доступ пользователя на основе его токена и требуемых ролей.
     *
     * @param bearerToken   токен авторизации пользователя
     * @param requiredRoles список ролей, которые необходимо проверить для данного пользователя
     * @throws AccessDeniedException если у пользователя нет необходимых прав доступа
     */
    @Override
    public void checkAccess(String bearerToken, List<String> requiredRoles) {
        if (!userServiceClient.checkUserAccess(bearerToken, requiredRoles)) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
