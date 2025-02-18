package com.example.ExpenseManagementApp.DTO;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

        @Nullable
        private String userName;
        private String email;
        private String password;


        public UserDTO(@Nullable String userName, String email, String password) {
                this.userName = userName;
                this.email = email;
                this.password = password;
        }
        public UserDTO() {
        }



        public String getUserName() {
                return userName;
        }

        public String getPassword() {
                return password;
        }

        public String getEmail() {
                return email;
        }

        public void setUserName(@Nullable String userName) {
                this.userName = userName;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        @Override
        public String toString() {
                return "UserDTO{" +
                        "userName='" + userName + '\'' +
                        ", email='" + email + '\'' +
                        ", password='" + password + '\'' +
                        '}';
        }



}
