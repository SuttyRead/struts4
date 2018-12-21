package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.DateRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
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
@Action(value = "/add", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/add.jsp"),
        @Result(name = "success", type = "redirect", location = "/home"),
        @Result(name = "accessDenied", location = "/WEB-INF/jsp/403.jsp"),
        @Result(name = "error", type = "redirect", location = "/login")
})
@Getter
@Setter
public class AddAction extends ActionSupport {

    private HttpServletRequest request = ServletActionContext.getRequest();
    private User user;

    @Validations(regexFields = {
            @RegexFieldValidator(regex = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password not pattern", fieldName = "user.password"),
            @RegexFieldValidator(regex = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password not pattern", fieldName = "user.confirmPassword"),
            @RegexFieldValidator(regex = "^[A-Z]{1}[a-z]{1,25}", message = "First name not pattern", fieldName = "user.firstName"),
            @RegexFieldValidator(regex = "^[A-Z]{1}[a-z]{1,25}", message = "Last name not pattern", fieldName = "user.lastName")
    }, dateRangeFields = {
            @DateRangeFieldValidator(max = "12/20/2018", message = "Invalid Date Range", fieldName = "user.birthday")
    }, emails = {
            @EmailValidator(message = "Email not pattern", fieldName = "user.email")
    })
    @Override
    public String execute() throws Exception {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return ERROR;
        }
        if (loggedInUser.getRole().getName().equals("ADMIN")) {
            if (request.getMethod().equals("GET")) {
                return INPUT;
            }
            if (request.getMethod().equals("POST")) {
                HibernateUserService hibernateUserService = new HibernateUserService();
                user.getRole().setId(user.getRole().getName().equals("ADMIN") ? 1L : 2L);
                hibernateUserService.create(user);
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
            HibernateUserService hibernateUserService = new HibernateUserService();
            if (hibernateUserService.findByLogin(user.getLogin()) != null){
                addFieldError("user.login", "This login already exist");
            }
            if (!user.getPassword().equals(user.getConfirmPassword())){
                addFieldError("user.confirmPassword", "Password don't match");
            }
        }
    }

}
