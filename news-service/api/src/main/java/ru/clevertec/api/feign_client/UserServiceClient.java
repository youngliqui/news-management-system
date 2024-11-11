package ru.clevertec.api.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.api.feign_client.config.UserServiceClientConfiguration;

import java.util.List;

@FeignClient(name = "user-service", url = "http://localhost:8082/api",
        configuration = UserServiceClientConfiguration.class)
public interface UserServiceClient {
    @GetMapping("/users/access")
    Boolean checkUserAccess(@RequestHeader("Authorization") String bearerToken,
                            @RequestParam List<String> roles);
}
