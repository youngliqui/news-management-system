package ru.clevertec.api.feign_client.access;

import java.util.List;

public interface AccessControlService {
    void checkAccess(String bearerToken, List<String> requiredRoles);
}
