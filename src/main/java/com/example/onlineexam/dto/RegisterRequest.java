package com.example.onlineexam.dto;

import com.example.onlineexam.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private Role role;
}
