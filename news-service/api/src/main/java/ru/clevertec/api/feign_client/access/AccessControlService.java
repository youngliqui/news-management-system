package ru.clevertec.api.feign_client.access;

import java.util.List;

/**
 * Интерфейс для управления доступом пользователей.
 * <p>
 * Этот интерфейс определяет методы для проверки доступа пользователей
 * на основе токена авторизации и необходимых ролей.
 * </p>
 */
public interface AccessControlService {
    void checkAccess(String bearerToken, List<String> requiredRoles);
}
