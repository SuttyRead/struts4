package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.*;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.service.HibernateUserService;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;

@Namespace(value = "/")
@Action(value = "/edit", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/edit.jsp"),
        @Result(name = "success", type = "redirect", location = "/home?successfullyUpdated"),
        @Result(name = "accessDenied", location = "/WEB-INF/jsp/403.jsp"),
        @Result(name = "error", type = "redirect", location = "/login")
})

public class EditAction extends ActionSupport {

    private HttpServletRequest request = ServletActionContext.getRequest();

    @Getter
    @Setter
    private User user;

    @Validations(regexFields = {
            @RegexFieldValidator(regex = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password not pattern", fieldName = "user.password"),
            @RegexFieldValidator(regex = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password not pattern", fieldName = "user.confirmPassword"),
            @RegexFieldValidator(regex = "^[A-Z]{1}[a-z]{1,25}", message = "First name not pattern", fieldName = "user.firstName"),
            @RegexFieldValidator(regex = "^[A-Z]{1}[a-z]{1,25}", message = "Last name not pattern", fieldName = "user.lastName")
    }, dateRangeFields = {
            @DateRangeFieldValidator(max = "12/20/2018", message = "Enter correct birthday", fieldName = "user.birthday")
    }, emails = {
            @EmailValidator(message = "Email not pattern", fieldName = "user.email")
    })
    @Override
    public String execute() {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        HibernateUserService hibernateUserService = new HibernateUserService();
        if (loggedInUser == null) {
            return ERROR;
        }
        if (loggedInUser.getRole().getName().equals("ADMIN")) {
            String userId = request.getParameter("userId");
            if (request.getMethod().equals("GET")) {
                user = hibernateUserService.findById(Long.valueOf(userId));
                if (user == null) {
                    return ERROR;
                }
                return INPUT;
            }
            if (request.getMethod().equals("POST")) {
                user.getRole().setId(user.getRole().getName().equals("ADMIN") ? 1L : 2L);
                System.out.println(user);
                hibernateUserService.update(user);
                return SUCCESS;
            }
            return ERROR;
        } else {
            return "accessDenied";
        }
    }

    @Override
    public void validate() {
        if (request.getMethod().equals("POST")) {
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                addFieldError("user.confirmPassword", "Password don't match");
            }
        }
    }

}
