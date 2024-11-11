package ru.clevertec.api.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.api.feign_client.config.UserServiceClientConfiguration;

import java.util.List;

/**
 * Клиент для взаимодействия с сервисом пользователей.
 * <p>
 * Этот интерфейс использует Feign для выполнения HTTP-запросов к
 * удаленному сервису пользователей, предоставляя методы для проверки
 * доступа пользователей по их ролям.
 * </p>
 */
@FeignClient(name = "user-service", url = "http://localhost:8082/api",
        configuration = UserServiceClientConfiguration.class)
public interface UserServiceClient {

    /**
     * Проверяет доступ пользователя на основе его токена и ролей.
     *
     * @param bearerToken токен авторизации пользователя, который должен быть передан в заголовке
     * @param roles       список ролей, которые необходимо проверить для данного пользователя
     * @return true, если у пользователя есть хотя бы одна из указанных ролей; иначе false
     */
    @GetMapping("/users/access")
    Boolean checkUserAccess(@RequestHeader("Authorization") String bearerToken,
                            @RequestParam List<String> roles);
}
