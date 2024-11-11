package ru.clevertec.api.feign_client.access;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.api.feign_client.UserServiceClient;
import ru.clevertec.api.feign_client.exception.AccessDeniedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessControlServiceImpl implements AccessControlService {
    private final UserServiceClient userServiceClient;

    @Override
    public void checkAccess(String bearerToken, List<String> requiredRoles) {
        if (!userServiceClient.checkUserAccess(bearerToken, requiredRoles)) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
