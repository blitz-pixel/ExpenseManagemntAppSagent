package com.example.ExpenseManagementApp.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginDTO {
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


}
