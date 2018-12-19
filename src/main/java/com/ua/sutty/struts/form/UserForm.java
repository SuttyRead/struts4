package com.ua.sutty.struts.form;

import com.ua.sutty.struts.domain.Role;
import com.ua.sutty.struts.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForm {

    private Long id;

    private String login;

    private String password;

    private String confirmPassword;

    private String email;

    private String firstName;

    private String lastName;

    private String birthday;

    private String roleName;

    public User toUser(){
        Role role = roleName.equals("ADMIN") ? new Role(1L, "ADMIN") : new Role(2L, "USER");
        return User.builder()
                .login(login)
                .password(password)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthday(Date.valueOf(birthday))
                .role(role)
                .build();
    }

}
