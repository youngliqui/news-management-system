package ru.clevertec.api.feign_client;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.clevertec.api.feign_client.exception.AccessDeniedException;
import ru.clevertec.api.feign_client.exception.InternalServerErrorException;
import ru.clevertec.api.feign_client.exception.InvalidTokenException;
import ru.clevertec.api.feign_client.exception.NotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

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
