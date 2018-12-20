package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
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
@Action(value = "/edit", results = {
        @Result(name = "input", location = "/WEB-INF/jsp/edit.jsp"),
        @Result(name = "success", type = "redirect", location = "/home"),
        @Result(name = "accessDenied", location = "/WEB-INF/jsp/403.jsp"),
        @Result(name = "error", type = "redirect", location = "/login")
})
public class EditAction extends ActionSupport implements Preparable {

    private HttpServletRequest request = ServletActionContext.getRequest();

    private String idUser;
    private User user;
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
        HibernateUserService hibernateUserService = new HibernateUserService();
        if (loggedInUser == null) {
            return ERROR;
        }
        if (loggedInUser.getRole().getName().equals("ADMIN")) {
            String userId = request.getParameter("userId");
            if (userId == null){
                User userForEdit = (User) request.getSession().getAttribute("userForEdit");
//                if (userForEdit != null){
//                    user = userForEdit;
//                    return INPUT;
//                }
                return SUCCESS;
            }
            if (request.getMethod().equals("GET")) {
                user = hibernateUserService.findById(Long.valueOf(userId));
                request.getSession().setAttribute("userForEdit", user);
                if (user == null){
                    return ERROR;
                }
                return INPUT;
            }
            if (request.getMethod().equals("POST")) {
                HibernateRoleService hibernateRoleService = new HibernateRoleService();
                User someUser = User.builder()
                        .id(Long.valueOf(userId))
                        .login(login)
                        .password(password)
                        .email(email)
                        .firstName(firstName)
                        .lastName(lastName)
                        .birthday(new java.sql.Date(birthday.getTime()))
                        .role(hibernateRoleService.findByName(role))
                        .build();
                hibernateUserService.update(someUser);
                return SUCCESS;
            }
            return ERROR;
        } else {
            return "accessDenied";
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void prepare() throws Exception {

    }

}
