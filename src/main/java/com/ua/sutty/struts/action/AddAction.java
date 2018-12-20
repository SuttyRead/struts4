package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.DateRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.service.HibernateRoleService;
import com.ua.sutty.struts.service.HibernateUserService;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Namespace(value = "/")
@Action(value = "/add", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/add.jsp"),
        @Result(name = "success", type = "redirect", location = "/home"),
        @Result(name = "accessDenied", location = "/WEB-INF/jsp/403.jsp"),
        @Result(name = "error", type = "redirect", location = "/login")
})
public class AddAction extends ActionSupport {

    private HttpServletRequest request = ServletActionContext.getRequest();

    private String login;
    private String password;
    private String confirmPassword;
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String role;

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
                HibernateRoleService hibernateRoleService = new HibernateRoleService();
                User userInBase = hibernateUserService.findByLogin(login);
                if (userInBase == null) {
                    User user = User.builder()
                            .login(login)
                            .password(password)
                            .email(email)
                            .firstName(firstName)
                            .lastName(lastName)
                            .birthday(new java.sql.Date(birthday.getTime()))
                            .role(hibernateRoleService.findByName(role))
                            .build();
                    hibernateUserService.create(user);
                    request.getSession().setAttribute("users", hibernateUserService.findAll());
                }
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
            if (hibernateUserService.findByLogin(login) != null){
                addFieldError("login", "This login already exist");
            }
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getLogin() {
        return login;
    }

    @RegexFieldValidator(regex = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$", message = "Login not pattern")
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    @RegexFieldValidator(regex = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password not pattern")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @RegexFieldValidator(regex = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Password not pattern")
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    @EmailValidator(message = "Email not pattern")
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    @RegexFieldValidator(regex = "^[A-Z]{1}[a-z]{1,25}", message = "First name not pattern")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @RegexFieldValidator(regex = "^[A-Z]{1}[a-z]{1,25}", message = "Last name not pattern")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Date getBirthday() {
        return birthday;
    }

    @DateRangeFieldValidator(max = "12/20/2018", message = "Invalid Date Range")
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
