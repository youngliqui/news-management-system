package ru.clevertec.security.common.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    @Size(min = 5, max = 45, message = "The nickname must contain from 5 to 50 characters")
    @NotBlank(message = "The nickname cannot be empty")
    private String username;
}