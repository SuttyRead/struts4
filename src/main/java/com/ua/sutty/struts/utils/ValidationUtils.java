package com.ua.sutty.struts.utils;

import com.ua.sutty.struts.form.UserForm;
import com.ua.sutty.struts.service.HibernateUserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    private UserForm userForm;

    private String loginPattern = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
    private String passwordPattern = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$";
    private String namePattern = "^[A-Z]{1}[a-z]{1,25}";
    private String emailPattern = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";

    public boolean checkLoginPattern(String login) {
        return validatePattern(login, loginPattern);
    }

    public boolean checkPasswordPattern(String password) {
        return validatePattern(password, passwordPattern);
    }

    public boolean checkFirstNamePattern(String firstName) {
        return validatePattern(firstName, namePattern);
    }

    public boolean checkLastNamePattern(String lastName) {
        return validatePattern(lastName, namePattern);
    }

    public boolean checkEmailPattern(String email) {
        return validatePattern(email, emailPattern);
    }

    public boolean checkMatchPassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public boolean checkAlreadyExistLogin(String login) {
        HibernateUserService hibernateUserService = new HibernateUserService();
        return hibernateUserService.findByLogin(login) != null;
    }

    private boolean validatePattern(String param, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(param);
        return matcher.matches();
    }

}
