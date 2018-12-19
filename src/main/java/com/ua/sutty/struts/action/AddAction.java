package com.ua.sutty.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import com.ua.sutty.struts.domain.User;
import com.ua.sutty.struts.form.UserForm;
import com.ua.sutty.struts.service.HibernateUserService;
import lombok.Data;
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
//    private User user;
    private UserForm userForm;

    @Override
    public String execute() throws Exception {
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null){
            return ERROR;
        }
        if (loggedInUser.getRole().getName().equals("ADMIN")) {
            if (request.getMethod().equals("GET")) {
                return INPUT;
            }
            if (request.getMethod().equals("POST")){
//                System.out.println("User = " + user);
                HibernateUserService hibernateUserService = new HibernateUserService();
                User userInBase = hibernateUserService.findByLogin(userForm.getLogin());
                if (userInBase == null){
//                    userForm.getRole().setId(user.getRole().getName().equals("USER") ? 2L : 1L);
                    hibernateUserService.create(userForm.toUser());
                    request.getSession().setAttribute("users", hibernateUserService.findAll());
                }
                return SUCCESS;
            }
            return ERROR;
        } else {
            return "accessDenied";
        }
    }

}
