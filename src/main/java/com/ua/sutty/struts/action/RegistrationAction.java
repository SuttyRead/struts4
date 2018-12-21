package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.DateRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.ua.sutty.struts.domain.Role;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.service.HibernateUserService;
import com.ua.sutty.struts.validator.CaptchaValidator;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;

@Namespace(value = "/")
@Action(value = "/registration", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/registration.jsp"),
        @Result(name = "success", type = "redirect", location = "/login?successfullyRegistration"),
        @Result(name = "error", type = "redirect", location = "/registration")
})
public class RegistrationAction extends ActionSupport {

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
        if (loggedInUser != null) {
            return ERROR;
        }
        if (request.getMethod().equals("GET")) {
            return INPUT;
        }
        if (request.getMethod().equals("POST")) {
            CaptchaValidator captchaValidator = new CaptchaValidator();
            if (!captchaValidator.verify(request.getParameter("g-recaptcha-response"))) {
                addActionError("Fill captcha please");
                return INPUT;
            }
            HibernateUserService hibernateUserService = new HibernateUserService();
            user.setRole(new Role(2L, "USER"));
            hibernateUserService.create(user);
            return SUCCESS;
        }
        return ERROR;
    }

    @Override
    public void validate() {
        if (request.getMethod().equals("POST")) {
            HibernateUserService hibernateUserService = new HibernateUserService();
            if (hibernateUserService.findByLogin(user.getLogin()) != null) {
                addFieldError("user.login", "This login already exist");
            }
            if (!user.getPassword().equals(user.getConfirmPassword())) {
                addFieldError("user.confirmPassword", "Password don't match");
            }
        }
    }

}
