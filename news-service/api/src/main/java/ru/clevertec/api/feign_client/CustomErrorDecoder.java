package ru.clevertec.api.feign_client;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.clevertec.api.feign_client.exception.AccessDeniedException;
import ru.clevertec.api.feign_client.exception.InternalServerErrorException;
import ru.clevertec.api.feign_client.exception.InvalidTokenException;
import ru.clevertec.api.feign_client.exception.NotFoundException;

/**
 * Пользовательский декодер ошибок для обработки ответов Feign.
 * <p>
 * Этот класс реализует интерфейс {@link ErrorDecoder} и предоставляет
 * логику для обработки различных кодов статуса HTTP, возвращаемых
 * удаленным сервисом. В зависимости от кода статуса, он генерирует
 * соответствующие исключения.
 * </p>
 */
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    /**
     * Декодирует ошибку на основе кода статуса HTTP.
     *
     * @param s        идентификатор метода, который вызвал ошибку
     * @param response ответ от удаленного сервиса с кодом статуса
     * @return соответствующее исключение в зависимости от кода статуса
     */
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 401 -> new InvalidTokenException("Invalid token");
            case 404 -> new NotFoundException("User not found");
            case 403 -> new AccessDeniedException("Access denied");
            case 500 -> new InternalServerErrorException("Internal server error occurred");
            default -> defaultErrorDecoder.decode(s, response);
        };
    }
}
