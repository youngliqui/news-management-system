package ru.clevertec.security.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @Size(min = 5, max = 45, message = "The nickname must contain from 5 to 50 characters")
    @NotBlank(message = "The nickname cannot be empty")
    private String username;

    @Size(min = 5, max = 64, message = "The password must be between 5 and 255 characters long")
    @NotBlank(message = "The password cannot be empty")
    private String password;
}