package com.example.ExpenseManagementApp.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;



@Getter
@Setter
public class RegisterDTO {

        @NotBlank(message = "User name is required")
        private String userName;

        @Email(message = "Email should be valid")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        public String getUserName() {
                return userName;
        }

        public String getPassword() {
                return password;
        }

        public String getEmail() {
                return email;
        }



}
