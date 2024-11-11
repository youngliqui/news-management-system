package ru.clevertec.security.core.service.access;

import java.util.List;

public interface AccessService {
    boolean checkUserAccess(String bearerToken, List<String> requiredRoles);
}
