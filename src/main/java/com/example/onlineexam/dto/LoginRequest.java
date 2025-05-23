package com.example.onlineexam.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    @Email(message = "Email должен быть корректным")
    private String email;

    @NotBlank
    private String password;
}
